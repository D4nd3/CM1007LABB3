package com.example.search.repositories;

import java.util.List;

import com.example.search.models.Staff;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StaffRepository implements PanacheRepository<Staff> {
  
    public List<Staff> findByName(String name) {
        String query = "lower(firstName) like lower(?1) or lower(lastName) like lower(?1)";
        return find(query, "%" + name + "%").list();
    }
}