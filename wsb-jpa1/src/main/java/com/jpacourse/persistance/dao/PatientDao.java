package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import java.time.LocalDateTime;
import java.util.List;

public interface PatientDao extends Dao<PatientEntity, Long> {
    PatientEntity addVisit(Long patientId, Long doctorId, LocalDateTime visitDate, String description);
    List<VisitEntity> findAllVisitsByPatientId(Long patientId);
}
