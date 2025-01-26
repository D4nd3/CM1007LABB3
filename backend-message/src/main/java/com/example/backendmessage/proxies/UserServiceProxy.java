package com.example.backendmessage.proxies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.backendmessage.dto.responses.users.UserResponse;

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
}