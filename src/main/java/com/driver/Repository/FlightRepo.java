package com.driver.Repository;

import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

import static com.driver.model.City.*;

@Repository
public class FlightRepo {
    private HashMap<Integer, Flight> flightRepo=new HashMap<>(); // flightId-->flightobj

    public HashMap<Integer, Flight> getFlightRepo() {
        return flightRepo;
    }

    public void addFlight(Flight flight){
        flightRepo.put(flight.getFlightId(), flight);
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        double minduration=Integer.MAX_VALUE+100.0;
        for(int id:flightRepo.keySet()){
            if(minduration>flightRepo.get(id).getDuration())
                minduration=flightRepo.get(id).getDuration();
        }
        if(minduration==Integer.MAX_VALUE+100.0)
            return -1.0;
        else return minduration;
    }

    public String getAirportNameFromFlightId(int flightId){
        if(!flightRepo.containsKey(flightId))
            return null;
        switch(flightRepo.get(flightId).getFromCity()){
            case KANPUR:
                return KANPUR.name();
            case DELHI:
                return DELHI.name();
            case CHANDIGARH:
                return CHANDIGARH.name();
            case BANGLORE:
                return BANGLORE.name();
            case JAIPUR:
                return JAIPUR.name();
            case KOLKATA:
                return KOLKATA.name();
            case CHENNAI:
                return CHENNAI.name();
            default: return null;
        }
    }
}
