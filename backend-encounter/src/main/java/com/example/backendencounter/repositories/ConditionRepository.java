package com.example.backendencounter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backendencounter.models.Condition;

@Repository
public interface ConditionRepository extends JpaRepository<Condition, Integer> {
}