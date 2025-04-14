package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class PatientMapper {

    public static PatientTO mapToTO(final PatientEntity patientEntity) {
        if (patientEntity == null) return null;

        final PatientTO patientTO = new PatientTO();
        patientTO.setId(patientEntity.getId());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientTO.setEmail(patientEntity.getEmail());
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTO.setInsured(patientEntity.isInsured());
        patientTO.setAddress(AddressMapper.mapToTO(patientEntity.getAddress()));

        if (patientEntity.getVisits() != null) {
            List<VisitTO> visitTOs = new ArrayList<>();
            for (VisitEntity visit : patientEntity.getVisits()) {
                VisitTO visitTO = new VisitTO();
                visitTO.setId(visit.getId());
                visitTO.setDescription(visit.getDescription());
                visitTO.setTime(visit.getTime());

                if (visit.getDoctor() != null) {
                    visitTO.setDoctorFirstName(visit.getDoctor().getFirstName());
                    visitTO.setDoctorLastName(visit.getDoctor().getLastName());
                }

                if (visit.getTreatments() != null) {
                    List<String> treatmentTypes = visit.getTreatments().stream()
                            .map(treatment -> treatment.getType().name())
                            .collect(Collectors.toList());
                    visitTO.setTreatmentTypes(treatmentTypes);
                }

                visitTOs.add(visitTO);
            }
            patientTO.setVisits(visitTOs);
        }

        return patientTO;
    }

    public static PatientEntity mapToEntity(final PatientTO patientTO) {
        if (patientTO == null) return null;

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientTO.getId());
        patientEntity.setFirstName(patientTO.getFirstName());
        patientEntity.setLastName(patientTO.getLastName());
        patientEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
        patientEntity.setEmail(patientTO.getEmail());
        patientEntity.setPatientNumber(patientTO.getPatientNumber());
        patientEntity.setDateOfBirth(patientTO.getDateOfBirth());
        patientEntity.setInsured(patientTO.isInsured());
        patientEntity.setAddress(AddressMapper.mapToEntity(patientTO.getAddress()));

        return patientEntity;
    }
}
