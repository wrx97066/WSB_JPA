package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.DoctorEntity;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VisitEntity> findAllVisitsByPatientId(Long patientId) {
        return entityManager.createQuery(
                        "SELECT v FROM VisitEntity v WHERE v.patient.id = :patientId",
                        VisitEntity.class
                )
                .setParameter("patientId", patientId)
                .getResultList();
    }

    @Override
    public PatientEntity addVisit(Long patientId, Long doctorId, LocalDateTime visitDate, String description) {
        PatientEntity patient = findOne(patientId);
        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

        if (patient != null && doctor != null) {
            VisitEntity visit = new VisitEntity();
            visit.setTime(visitDate);
            visit.setDescription(description);
            visit.setDoctor(doctor);
            visit.setPatient(patient);

            if (patient.getVisits() == null) {
                patient.setVisits(new java.util.ArrayList<>());
            }
            patient.getVisits().add(visit);

            return update(patient);
        }
        return null;
    }

    @Override
    public List<PatientEntity> findByLastName(String lastName) {
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p WHERE p.lastName = :lastName",
                        PatientEntity.class
                )
                .setParameter("lastName", lastName)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithMoreThanXVisits(long minVisits) {
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p WHERE SIZE(p.visits) > :minVisits",
                        PatientEntity.class
                )
                .setParameter("minVisits", minVisits)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findByDateOfBirthAfter(LocalDate date) {
        return entityManager.createQuery(
                        "SELECT p FROM PatientEntity p WHERE p.dateOfBirth > :date",
                        PatientEntity.class
                )
                .setParameter("date", date)
                .getResultList();
    }
}
