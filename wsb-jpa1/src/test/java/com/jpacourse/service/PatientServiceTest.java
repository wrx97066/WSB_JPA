package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldDeletePatientAndVisitsButNotDoctors() {
        // Given
        DoctorEntity doctor = createTestDoctor();
        PatientEntity patient = createTestPatientWithVisits(doctor);

        // When
        patientService.delete(patient.getId());

        // Then
        assertNull(patientDao.findOne(patient.getId()));
        assertTrue(patientDao.findAllVisitsByPatientId(patient.getId()).isEmpty());
        assertNotNull(doctorDao.findOne(doctor.getId()));
    }

    @Test
    public void shouldReturnPatientTOWithInsuredField() {
        // Given
        PatientEntity patient = createTestPatient();
        patient.setInsured(true);
        patientDao.save(patient);

        // When
        PatientTO result = patientService.findById(patient.getId());

        // Then
        assertTrue(result.isInsured());
        assertEquals(patient.getFirstName(), result.getFirstName());
        assertEquals(patient.getLastName(), result.getLastName());
    }

    @Test
    public void shouldFindAllVisitsByPatientId() {
        // Zakładając, że w data.sql pacjent 201 ma 2 wizyty (ID 301 i 302)
        List<VisitTO> visits = patientService.findAllVisitsByPatientId(201L);

        assertNotNull(visits);
        assertEquals(2, visits.size());
        assertTrue(visits.stream().anyMatch(v -> v.getId().equals(301L)));
        assertTrue(visits.stream().anyMatch(v -> v.getId().equals(302L)));
    }

    private PatientEntity createTestPatient() {
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Jan");
        patient.setLastName("Kowalski");
        patient.setPatientNumber("P123");
        patient.setDateOfBirth(LocalDate.of(1980, 1, 1));
        patient.setInsured(false);
        return patientDao.save(patient);
    }

    private PatientEntity createTestPatientWithVisits(DoctorEntity doctor) {
        PatientEntity patient = createTestPatient();

        VisitEntity visit = new VisitEntity();
        visit.setTime(LocalDate.now().atStartOfDay());
        visit.setDescription("Kontrolna wizyta");
        visit.setDoctor(doctor);
        visit.setPatient(patient);

        patient.getVisits().add(visit);
        return patientDao.update(patient);
    }

    private DoctorEntity createTestDoctor() {
        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Anna");
        doctor.setLastName("Nowak");
        doctor.setSpecialization("Kardiolog");
        return doctorDao.save(doctor);
    }
}
