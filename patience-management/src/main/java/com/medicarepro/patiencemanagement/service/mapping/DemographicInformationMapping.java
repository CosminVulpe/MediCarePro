package com.medicarepro.patiencemanagement.service.mapping;

import com.medicarepro.patiencemanagement.controller.dto.DemographicInformationDTO;
import com.medicarepro.patiencemanagement.service.entity.DemographicInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DemographicInformationMapping {
    DemographicInformationMapping INSTANCE = Mappers.getMapper(DemographicInformationMapping.class);

    @Mapping(source = "timeOfBirth", target = "timeOfBirth", dateFormat = "yyyy-MM-dd")
    DemographicInformationDTO convertToDto(DemographicInformation source);

    @Mapping(source = "timeOfBirth", target = "timeOfBirth", dateFormat = "yyyy-MM-dd")
    DemographicInformation convertToEntity(DemographicInformationDTO source);
}
