package com.example.backenduser.services;

import com.example.backenduser.repositories.*;
import com.example.backenduser.util.*;
import com.example.backenduser.dto.responses.users.LocationResponse;
import com.example.backenduser.interfaces.ILocationService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationService implements ILocationService {

    @Autowired
    private LocationRepository locationRepository;

    public IResult getAll(){
        var locations = locationRepository.findAll();

        if (locations == null){
            return new Result<>(false,"Repository Issues");
        }

        var result = locations.stream().map(Converter::convertLocation).collect(Collectors.toList());


        return new Result<List<LocationResponse>>(true,"",result);
    }

    public IResult getById(int id){
        var location = locationRepository.findById(id);

        if (location.isEmpty()){
            return new Result<>(false,"No Sutch Location");
        }

        var result = Converter.convertLocation(location.get());

        return new Result<LocationResponse>(true,"",result);
    }
}