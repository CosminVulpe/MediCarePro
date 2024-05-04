package com.healthcaremanagement.service.mapping;

import com.healthcaremanagement.controller.dto.ContactInfoDTO;
import com.healthcaremanagement.service.entity.ContactInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ContactInfoMapping {
    ContactInfoMapping INSTANCE = Mappers.getMapper(ContactInfoMapping.class);


    ContactInfoDTO mapToDto(ContactInfo source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    ContactInfo mapToEntity(ContactInfoDTO source);
}
