package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldAddVisitToPatient() {
        // Given
        PatientEntity patient = createTestPatient();
        DoctorEntity doctor = createTestDoctor();

        LocalDateTime visitTime = LocalDateTime.now();
        String description = "Kontrolna wizyta";

        // When
        PatientEntity updatedPatient = patientDao.addVisit(
                patient.getId(),
                doctor.getId(),
                visitTime,
                description
        );

        // Then
        assertNotNull(updatedPatient);
        assertEquals(1, updatedPatient.getVisits().size());

        VisitEntity visit = updatedPatient.getVisits().get(0);
        assertEquals(visitTime, visit.getTime());
        assertEquals(description, visit.getDescription());
        assertEquals(doctor.getId(), visit.getDoctor().getId());
    }

    private PatientEntity createTestPatient() {
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Jan");
        patient.setLastName("Kowalski");
        patient.setPatientNumber("P123");
        patient.setDateOfBirth(LocalDate.of(1980, 1, 1));
        patient.setInsured(true);
        entityManager.persist(patient);
        return patient;
    }

    private DoctorEntity createTestDoctor() {
        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Anna");
        doctor.setLastName("Nowak");
        doctor.setSpecialization("Kardiolog");
        entityManager.persist(doctor);
        return doctor;
    }
}
