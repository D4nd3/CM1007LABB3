package com.example.backendencounter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.backendencounter.models.Observation;

@Repository
public interface ObservationRepository extends JpaRepository<Observation, Integer> {
}