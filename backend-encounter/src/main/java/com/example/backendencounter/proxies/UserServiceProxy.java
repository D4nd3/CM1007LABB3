package com.example.backendencounter.proxies;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.parsing.Location;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
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

    public UserResponse findUserById(String userId, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = backendUserUrl + "/users/byId?id=" + userId;
        return restTemplate.exchange(url, HttpMethod.GET, entity, UserResponse.class).getBody();
    }

    public List<LocationResponse> getLocations(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = backendUserUrl + "/locations/";

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<LocationResponse>>() {}
        ).getBody();
    }

    public LocationResponse findLocationById(int id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>(headers);
        String url = backendUserUrl + "/locations/byId?id=" + id;
        return restTemplate.exchange(url, HttpMethod.GET, entity, LocationResponse.class).getBody();
    }
}