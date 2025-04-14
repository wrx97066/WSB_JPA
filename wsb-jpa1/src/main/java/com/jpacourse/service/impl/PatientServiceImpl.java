package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientDao patientDao;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    public PatientTO findById(Long id) {
        final PatientEntity entity = patientDao.findOne(id);
        return PatientMapper.mapToTO(entity);
    }

    @Override
    public PatientTO save(PatientTO patientTO) {
        PatientEntity entity = PatientMapper.mapToEntity(patientTO);
        PatientEntity savedEntity = patientDao.save(entity);
        return PatientMapper.mapToTO(savedEntity);
    }

    @Override
    public void delete(Long id) {
        patientDao.delete(id);
    }

    @Override
    public PatientTO addVisit(Long patientId, Long doctorId, LocalDateTime visitDate, String description) {
        PatientEntity updatedPatient = patientDao.addVisit(patientId, doctorId, visitDate, description);
        return PatientMapper.mapToTO(updatedPatient);
    }
}