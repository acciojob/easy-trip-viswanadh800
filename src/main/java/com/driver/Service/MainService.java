package com.driver.Service;

import com.driver.Repository.AirportRepo;
import com.driver.Repository.FlightRepo;
import com.driver.Repository.PassengerRepo;
import com.driver.Repository.RepoModifier;
import com.driver.model.Airport;
import com.driver.model.City;
import com.driver.model.Flight;
import com.driver.model.Passenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class MainService {
    @Autowired
    AirportRepo airportRepo;
    @Autowired
    FlightRepo flightRepo;
    @Autowired
    PassengerRepo passengerRepo;
    @Autowired
    RepoModifier repoModifier;

    public void addPassenger(Passenger pasenger){
        passengerRepo.addPassenger(pasenger);
    }

    public void addFlight(Flight flight){
        flightRepo.addFlight(flight);
    }

    public void addAirport(Airport airport){
        airportRepo.addAirport(airport);
    }

    public String bookATicket(int flightId, int passengerId){
        return repoModifier.bookATicket(flightId,passengerId);
    }

    public String getLargestAirport(){
        return airportRepo.getLargestAirportName();
    }

    public double getShortestDurationOfPossibleBetweenTwoCities(City fromCity, City toCity){
        return flightRepo.getShortestDurationOfPossibleBetweenTwoCities(fromCity,toCity);
    }

    public String cancelATicket(int flightId, int passengerId){
        return repoModifier.cancelATicket(flightId,passengerId);
    }

    public int countOfBookingsDoneByPassengerAllCombined(int passengerId){
        return repoModifier.countOfBookingsDoneByPassengerAllCombined(passengerId);
    }

    public int getNumberOfPeopleOn(Date date, String airportName){
        return repoModifier.getNumberOfPeopleOn(date,airportName);
    }

    public int calculateFlightFare(int flightId){
        return repoModifier.calculateFlightFare(flightId);
    }

    public String getAirportNameFromFlightId(int flightId){
        return flightRepo.getAirportNameFromFlightId(flightId);
    }

    public int calculateRevenueOfAFlight(int flightId){
        return repoModifier.calculateRevenueOfAFlight(flightId);
    }
}
