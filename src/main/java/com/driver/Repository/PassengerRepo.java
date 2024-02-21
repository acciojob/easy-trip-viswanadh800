package com.driver.Repository;

import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class PassengerRepo {
    private HashMap<Integer, Passenger> passengerRepo=new HashMap<>(); //passengerId --> passengerObj

    public HashMap<Integer, Passenger> getPassengerRepo() {
        return passengerRepo;
    }

    public void addPassenger(Passenger passenger){
        passengerRepo.put(passenger.getPassengerId(), passenger);
    }


}
