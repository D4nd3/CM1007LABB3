package com.example.backenduser.models;

import com.example.backenduser.converters.RoleConverter;
import com.example.backenduser.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    private String id;

    private String firstName;
    private String lastName;

    @Convert(converter = RoleConverter.class)
    @Column(name = "role_id", nullable = false)
    private Role role;

    public String getFullName(){
        return String.format("%s %s",firstName, lastName);
    }
}