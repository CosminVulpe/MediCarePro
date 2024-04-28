package com.medicarepro.patiencemanagement.service.mapping;

import com.medicarepro.patiencemanagement.controller.dto.MedicalHistoryDTO;
import com.medicarepro.patiencemanagement.service.entity.MedicalHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MedicalHistoryMapping {
    MedicalHistoryMapping INSTANCE = Mappers.getMapper(MedicalHistoryMapping.class);

    MedicalHistoryDTO convertToDto(MedicalHistory source);

    MedicalHistory convertToEntity(MedicalHistoryDTO source);
}
