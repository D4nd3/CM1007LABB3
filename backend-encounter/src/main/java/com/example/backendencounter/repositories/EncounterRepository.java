package com.example.backendencounter.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backendencounter.models.Encounter;

@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Integer> {
    @Query("SELECT e FROM Encounter e WHERE e.staff_id = :id")
    List<Encounter> findByStaffId(@Param("id") int id);
    @Query("SELECT e FROM Encounter e WHERE e.patient_id = :id")
    List<Encounter> findByPatientId(@Param("id") int id);
}