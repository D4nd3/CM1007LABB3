package com.example.backenduser.util;

import com.example.backenduser.dto.responses.users.*;
import com.example.backenduser.models.*;

public class Converter {

    public static UserResponse convertUser(User user) {
        return new UserResponse(user.getId(), user.getFullName(),user.getRole());
    }

    public static LocationResponse convertLocation(Location location) {
        if (location == null) {
            return null;
        }
        return new LocationResponse(location.getId(), location.getName(), location.getAddress());
    }
}