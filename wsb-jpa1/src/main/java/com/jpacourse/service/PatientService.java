package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;

import java.time.LocalDateTime;

public interface PatientService {
    PatientTO findById(final Long id);
    PatientTO save(final PatientTO patientTO);
    void delete(final Long id);
    PatientTO addVisit(Long patientId, Long doctorId, LocalDateTime visitDate, String description);
}