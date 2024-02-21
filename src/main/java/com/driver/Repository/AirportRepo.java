package com.driver.Repository;

import com.driver.model.Airport;
import com.driver.model.Passenger;
import org.springframework.stereotype.Repository;

import java.util.HashMap;

@Repository
public class AirportRepo {
    private HashMap<String, Airport> airportRepo; //Airport name --> AirportObj

    public AirportRepo(){
        airportRepo=new HashMap<>();
    }

    public HashMap<String, Airport> getAirportRepo() {
        return airportRepo;
    }

    public void addAirport(Airport airport){
        airportRepo.put(airport.getAirportName(),airport);
    }

    public String getLargestAirportName(){
        String name=null;
        int terminals=Integer.MIN_VALUE;
        for(String key:airportRepo.keySet()){
            if(airportRepo.get(key).getNoOfTerminals()>=terminals){
                if(airportRepo.get(key).getNoOfTerminals()==terminals){
                //    assert name != null;
                    if(key.compareTo(name)>0){
                        name=key;
                        terminals=airportRepo.get(name).getNoOfTerminals();
                    }
                }
                else{
                    name=key;
                    terminals=airportRepo.get(name).getNoOfTerminals();
                }
            }
        }
        return name;
    }
}
