package com.medicarepro.patiencemanagement.service.mapping;

import com.medicarepro.patiencemanagement.controller.dto.ContractInformationDTO;
import com.medicarepro.patiencemanagement.service.entity.ContractInformation;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContractInformationMapping {
    ContractInformationMapping INSTANCE = Mappers.getMapper(ContractInformationMapping.class);

    ContractInformationDTO convertToDto(ContractInformation source);
}
