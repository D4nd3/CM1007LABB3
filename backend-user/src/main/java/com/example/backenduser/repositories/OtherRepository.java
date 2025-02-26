package com.example.backenduser.repositories;

import com.example.backenduser.models.Practitioner;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtherRepository extends JpaRepository<Practitioner, String> {
}