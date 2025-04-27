package com.jpacourse.service.impl;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientServiceImpl implements PatientService {

    private final PatientDao patientDao;
    private final VisitMapper visitMapper;

    @Autowired
    public PatientServiceImpl(PatientDao patientDao, VisitMapper visitMapper) {
        this.patientDao = patientDao;
        this.visitMapper = visitMapper;
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

    @Override
    public List<VisitTO> findAllVisitsByPatientId(Long patientId) {
        List<VisitEntity> visits = patientDao.findAllVisitsByPatientId(patientId);
        return visits.stream()
                .map(visitMapper::mapToTO)
                .collect(Collectors.toList());
    }
}
