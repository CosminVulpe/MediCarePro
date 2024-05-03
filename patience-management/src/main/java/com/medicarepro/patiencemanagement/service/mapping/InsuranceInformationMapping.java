package com.medicarepro.patiencemanagement.service.mapping;

import com.medicarepro.patiencemanagement.controller.dto.InsuranceInformationDTO;
import com.medicarepro.patiencemanagement.service.entity.InsuranceInformation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InsuranceInformationMapping {
    InsuranceInformationMapping INSTANCE = Mappers.getMapper(InsuranceInformationMapping.class);

    @Mapping(source = "coverageDateStart", target = "coverageDateStart", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "coverageDateEnd", target = "coverageDateEnd", dateFormat = "yyyy-MM-dd")
    InsuranceInformationDTO convertToDto(InsuranceInformation source);

    @Mapping(source = "coverageDateStart", target = "coverageDateStart", dateFormat = "yyyy-MM-dd")
    @Mapping(source = "coverageDateEnd", target = "coverageDateEnd", dateFormat = "yyyy-MM-dd")
    InsuranceInformation convertToEntity(InsuranceInformationDTO source);

}
