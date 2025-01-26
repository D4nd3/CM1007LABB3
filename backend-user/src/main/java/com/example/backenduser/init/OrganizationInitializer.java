package com.example.backenduser.init;

import com.example.backenduser.models.Organization;
import com.example.backenduser.models.Location;
import com.example.backenduser.repositories.LocationRepository;
import com.example.backenduser.repositories.OrganizationRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class OrganizationInitializer implements CommandLineRunner {

    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public void run(String... args) throws Exception {;
        var organizations = organizationRepository.findAll();

        if (organizations.isEmpty()) {
            Organization organization1 = new Organization();
            Organization organization2 = new Organization();
            Organization organization3 = new Organization();
            organization1.setName("JVA");
            organization1.setAddress("Byxlösa 1, 123 45, Ankeborg");
            organization1.setEmail("joakim@von.anka");
            organization1.setPhoneNumber("123456789");
            var loc1 = locationRepository.findByName("Ankeborg");
            if(loc1!= null){
                var locs = new ArrayList<Location>();
                locs.add(loc1);
                organization1.setLocations(locs);
            }
            organizationRepository.save(organization1);
            organization2.setName("Pippi AB");
            organization2.setAddress("I Våra Hjärtan 3, 99999, Nangiala");
            organization2.setEmail("kling@och.klang");
            organization2.setPhoneNumber("112");
            var loc2 = locationRepository.findByName("Villevillekulla");
            if(loc2!= null){
                var locs = new ArrayList<Location>();
                locs.add(loc2);
                organization2.setLocations(locs);
            }
            organizationRepository.save(organization2);
            organization3.setName("Spawnpoint");
            organization3.setAddress("Norr Mälarstrand 08, 00008, Stockholm");
            organization3.setEmail("joakim@von.anka");
            organization3.setPhoneNumber("123456789");
            var loc3 = locationRepository.findByName("Zero8 AB");
            if(loc3 != null){
                var locs = new ArrayList<Location>();
                locs.add(loc3);
                organization3.setLocations(locs);
            }
            organizationRepository.save(organization3);
        }
    }
}