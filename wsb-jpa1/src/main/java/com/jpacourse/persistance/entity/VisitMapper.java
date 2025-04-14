package com.jpacourse.mapper;

import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.MedicalTreatmentEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.enums.TreatmentType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VisitMapper {

    public VisitTO toTO(VisitEntity entity) {
        if (entity == null) return null;

        VisitTO visitTO = new VisitTO();
        visitTO.setId(entity.getId());
        visitTO.setDescription(entity.getDescription());
        visitTO.setTime(entity.getTime());

        // Mapowanie imienia i nazwiska lekarza
        if (entity.getDoctor() != null) {
            visitTO.setDoctorFirstName(entity.getDoctor().getFirstName());
            visitTO.setDoctorLastName(entity.getDoctor().getLastName());
        }

        // Mapowanie typów zabiegów jako Stringi
        if (entity.getTreatments() != null) {
            List<String> treatmentTypes = entity.getTreatments().stream()
                    .map(treatment -> treatment.getType().name()) // 👈 Konwersja enum → String
                    .collect(Collectors.toList());
            visitTO.setTreatmentTypes(treatmentTypes);
        }

        return visitTO;
    }
}
