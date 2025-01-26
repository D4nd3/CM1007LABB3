package com.example.search.repositories;

import java.util.List;

import com.example.search.models.Condition;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ConditionRepository implements PanacheRepository<Condition> {

    public List<Condition> findByName(String name) {
        return find("name like ?1", "%" + name + "%").list();
    }

    public List<Condition> findByDescription(String description) {
        return find("description like ?1", "%" + description + "%").list();
    }
}