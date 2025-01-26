package com.example.search.repositories;

import java.util.List;

import com.example.search.models.Patient;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PatientRepository implements PanacheRepository<Patient> {
  
    public List<Patient> findByName(String name) {
        String query = "lower(firstName) like lower(?1) or lower(lastName) like lower(?1)";
        return find(query, "%" + name + "%").list();
    }
}