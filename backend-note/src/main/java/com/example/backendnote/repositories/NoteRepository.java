package com.example.backendnote.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.backendnote.models.Note;

@Repository
public interface NoteRepository extends JpaRepository<Note, Integer> {
    @Query("SELECT n FROM Note n WHERE n.patient_id = :id")
    List<Note> findByPatientId(@Param("id") int id);
    @Query("SELECT n FROM Note n WHERE n.staff_id = :id")
    List<Note> findByStaffId(@Param("id") int id);
}