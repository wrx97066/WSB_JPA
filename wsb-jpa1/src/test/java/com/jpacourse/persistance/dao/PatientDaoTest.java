package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.OptimisticLockException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
public class PatientDaoTest {

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void shouldThrowOptimisticLockExceptionOnConcurrentUpdate() {
        PatientEntity patient = createTestPatient();
        entityManager.persist(patient);
        entityManager.flush();

        PatientEntity firstInstance = patientDao.findOne(patient.getId());
        firstInstance.setFirstName("Pierwsza zmiana");
        patientDao.update(firstInstance);

        PatientEntity secondInstance = new PatientEntity();
        secondInstance.setId(patient.getId());
        secondInstance.setVersion(patient.getVersion());
        secondInstance.setLastName("Konfliktowa zmiana");
        secondInstance.setFirstName(patient.getFirstName());
        secondInstance.setPatientNumber(patient.getPatientNumber());

        assertThrows(OptimisticLockException.class, () -> {
            patientDao.update(secondInstance);
            entityManager.flush();
        });
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
}
