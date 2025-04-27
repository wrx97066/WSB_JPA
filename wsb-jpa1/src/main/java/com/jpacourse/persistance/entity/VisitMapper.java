package com.jpacourse.mapper;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VisitMapper {

    // Zmieniono nazwę metody z toTO() na mapToTO()
    public VisitTO mapToTO(VisitEntity entity) {
        if (entity == null) return null;

        VisitTO visitTO = new VisitTO();
        visitTO.setId(entity.getId());
        visitTO.setDescription(entity.getDescription());
        visitTO.setTime(entity.getTime());

        if (entity.getDoctor() != null) {
            visitTO.setDoctorFirstName(entity.getDoctor().getFirstName());
            visitTO.setDoctorLastName(entity.getDoctor().getLastName());
        }

        if (entity.getTreatments() != null) {
            List<String> treatmentTypes = entity.getTreatments().stream()
                    .map(treatment -> treatment.getType().name())
                    .collect(Collectors.toList());
            visitTO.setTreatmentTypes(treatmentTypes);
        }

        return visitTO;
    }
}
