package com.example.search.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.example.search.models.Encounter;
import com.example.search.models.Patient;
import com.example.search.models.Practitioner;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PractitionerRepository implements PanacheRepository<Practitioner> {

    public List<Patient> findPatientsByPractitioner(int practitionerId) {
    String query = """
        SELECT DISTINCT e.patient FROM Encounter e
        WHERE e.staff.id = :practitionerId
        """;
      return getEntityManager().createQuery(query, Patient.class)
            .setParameter("practitionerId", practitionerId)
            .getResultList();
    }

    public List<Encounter> findEncountersByPractitionerAndDate(int practitionerId, Date date) {
        String query = """
            SELECT e FROM Encounter e 
            JOIN e.observations o 
            WHERE e.staff.id = ?1 AND DATE(o.visitDate) = DATE(?2)
        """;
        return getEntityManager().createQuery(query, Encounter.class)
                .setParameter(1, practitionerId)
                .setParameter(2, date)
                .getResultList();
    }
}