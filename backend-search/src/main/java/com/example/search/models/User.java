package com.example.search.models;

import com.example.search.converters.RoleConverter;
import com.example.search.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;



@Setter
@Getter
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    private String id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Convert(converter = RoleConverter.class)
    @Column(name = "role_id", nullable = false)
    private Role role;

    public String getFullName(){
        return String.format("%s %s",firstName, lastName);
    }
}