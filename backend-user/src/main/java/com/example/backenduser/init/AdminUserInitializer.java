package com.example.backenduser.init;

import com.example.backenduser.enums.Role;
import com.example.backenduser.models.User;
import com.example.backenduser.repositories.UserRepository;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public void run(String... args) throws Exception {
        var admin = Role.ADMIN;
        var roleList = new ArrayList<Role>();
        roleList.add(admin);
        var users = userRepository.findByRoleIn(roleList);

        if (users.isEmpty()) {
            User adminUser = new User();
            adminUser.setFirstName("Admin");
            adminUser.setLastName("Adminsson");
            adminUser.setUsername("admin");
            adminUser.setEmail("admin@admin.se");
            adminUser.setPassword(passwordEncoder.encode("password123"));
            adminUser.setRole(Role.ADMIN);

            userRepository.save(adminUser);
        }
    }
}