package com.example.backendnote.proxies;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.backendnote.dto.responses.users.UserResponse;

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
}