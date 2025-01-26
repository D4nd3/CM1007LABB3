package com.example.search.util;

import com.example.search.dto.responses.*;
import com.example.search.models.*;

public class Converter {

    public static UserResponse convertUser(User user) {
        return new UserResponse(user.getId(), user.getFullName(),user.getRole());
    }
}