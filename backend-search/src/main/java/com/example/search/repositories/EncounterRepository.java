package com.example.search.repositories;

import com.example.search.models.Encounter;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.util.List;

@ApplicationScoped
public class EncounterRepository implements PanacheRepository<Encounter> {

    public List<Encounter> findByDate(LocalDate date) {
        return find("date", date).list();
    }
}