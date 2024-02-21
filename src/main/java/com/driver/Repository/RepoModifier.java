package com.driver.Repository;

import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Component
public class RepoModifier {
    @Autowired
    AirportRepo airportRepo=new AirportRepo();
    @Autowired
    FlightRepo flightRepo=new FlightRepo();
    @Autowired
    PassengerRepo passengerRepo=new PassengerRepo();

    private HashMap<String,Airport> airportMap=airportRepo.getAirportRepo();
    private HashMap<Integer,Flight> flightMap=flightRepo.getFlightRepo();
    private HashMap<Integer,Passenger> passengerMap=passengerRepo.getPassengerRepo();
    private HashMap<Integer, ArrayList<Integer>> flightPassengerPair=new HashMap<>();
    private HashMap<Integer, ArrayList<Integer>> passengerFlightPair=new HashMap<>();
    public String bookATicket(int flightId, int passengerId){
        if(flightMap.containsKey(flightId) && passengerMap.containsKey(passengerId)){
            Flight flight= flightMap.get(flightId);
            Passenger passenger=passengerMap.get(passengerId);
            if((passengerFlightPair.containsKey(passengerId) && findId(passengerFlightPair.get(passengerId),flightId)) || flight.getCurrCapacity()>=flight.getMaxCapacity())
                return "FAILURE";
            flight.setCurrCapacity(flight.getCurrCapacity()+1);
            if(!flightPassengerPair.containsKey(flightId))
                flightPassengerPair.put(flightId,new ArrayList<>());
            flightPassengerPair.get(flightId).add(passengerId);
            if(!passengerFlightPair.containsKey(passengerId))
                passengerFlightPair.put(passengerId,new ArrayList<>());
            passengerFlightPair.get(passengerId).add(flightId);
            return "SUCCESS";
        }
        else return "FAILURE";
    }

    private boolean findId(ArrayList<Integer> arr, int id){
        for(int i=0;i<arr.size();i++)
            if(arr.get(i)==id)
                return true;
        return false;
    }
    public String cancelATicket(int flightId, int passengerId){
        if(flightPassengerPair.containsKey(flightId) && findId(flightPassengerPair.get(flightId),passengerId)){
            Flight flight= flightMap.get(flightId);
            Passenger passenger=passengerMap.get(passengerId);
            flight.setCurrCapacity(flight.getCurrCapacity()-1);
            //updating flightPassengerPair
            ArrayList<Integer> arr=flightPassengerPair.get(flightId);
            arr.remove((Integer)passengerId);
            if(arr.size()==0)
                flightPassengerPair.remove(flightId);
            else
                flightPassengerPair.put(flightId,arr);
            //updating passengerFlightPair
            arr=passengerFlightPair.get(passengerId);
            arr.remove((Integer)flightId);
            if(arr.size()==0)
                passengerFlightPair.remove(passengerId);
            else
                passengerFlightPair.put(passengerId,arr);
            return "SUCCESS";
        }
        else return "FAILURE";
    }

    public int countOfBookingsDoneByPassengerAllCombined(int passengerId){
        if(passengerFlightPair.containsKey(passengerId))
            return passengerFlightPair.get(passengerId).size();
        return 0;
    }

    public int getNumberOfPeopleOn(Date date, String airportName){
        int count=0;
        if(!airportMap.containsKey(airportName))
            return 0;
        City dest=airportMap.get(airportName).getCity();
        for(int passengerId: passengerFlightPair.keySet()){
            ArrayList<Integer> flightIds=passengerFlightPair.get(passengerId);
            for(int i=0;i<flightIds.size();i++){
                if((flightMap.get(flightIds.get(i)).getFromCity().compareTo(dest)==0 || flightMap.get(flightIds.get(i)).getToCity().compareTo(dest)==0) && flightMap.get(flightIds.get(i)).getFlightDate().compareTo(date)==0 ) {
                    count++;
                }
            }
        }
        return count;
         /*
    Another way is to consider each flight whose flightDate is same as given date
    and same city as given airport's city.
    From all those selected flights, use flightPassengerRepo to find number of
    passengers on each flight and add it to count.
     */
    }

    public int calculateFlightFare(int flightId){
        int people=0;
        if(flightPassengerPair.containsKey(flightId))
            people=flightPassengerPair.get(flightId).size();
        return (3000+people*50);
    }

    public int calculateRevenueOfAFlight(int flightId){
        if(!flightPassengerPair.containsKey(flightId))
            return 0;
        ArrayList<Integer> arr=flightPassengerPair.get(flightId);
        int revenue=0;
        for(int i=0;i<arr.size();i++)
            revenue+=3000+i*50;
        return revenue;
    }
}
