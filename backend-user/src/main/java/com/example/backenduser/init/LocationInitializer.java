package com.example.backenduser.init;

import com.example.backenduser.models.Location;
import com.example.backenduser.repositories.LocationRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class LocationInitializer implements CommandLineRunner {

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public void run(String... args) throws Exception {;
        var locations = locationRepository.findAll();

        if (locations.isEmpty()) {
            Location location1 = new Location();
            Location location2 = new Location();
            Location location3 = new Location();
            location1.setName("Ankeborg");
            location1.setAddress("Byxlösa 1, 123 45, Ankeborg");
            locationRepository.save(location1);
            location2.setName("Villevillekulla");
            location2.setAddress("Astridlindgrensväg 2, 23456, Vimmerby");
            locationRepository.save(location2);
            location3.setName("Zero8 AB");
            location3.setAddress("Norr Mälarstrand 12, 11220, Stockholm");
            locationRepository.save(location3);
        }
    }
}