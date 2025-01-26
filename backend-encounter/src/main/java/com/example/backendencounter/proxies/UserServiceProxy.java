package com.example.backendencounter.proxies;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.backendencounter.dto.responses.users.LocationResponse;
import com.example.backendencounter.dto.responses.users.UserResponse;

@Service
public class UserServiceProxy {

    private final RestTemplate restTemplate;
    private final String backendUserUrl;

    public UserServiceProxy(RestTemplate restTemplate, @Value("${backend-user.url}") String backendUserUrl) {
        this.restTemplate = restTemplate;
        this.backendUserUrl = backendUserUrl;
    }

    public UserResponse findUserById(int userId) {
        String url = backendUserUrl + "/users/byId?id=" + userId;
        return restTemplate.getForObject(url, UserResponse.class);
    }

    public List<LocationResponse> getLocations() {
        String url = backendUserUrl + "/locations/";
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<LocationResponse>>() {}
        ).getBody();
    }

    public LocationResponse findLocationById(int id) {
        String url = backendUserUrl + "/locations/byId?id=" + id;
        return restTemplate.getForObject(url, LocationResponse.class);
    }
}