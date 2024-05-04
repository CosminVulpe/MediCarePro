package com.healthcaremanagement.service.mapping;

import com.healthcaremanagement.controller.dto.TimeSlotDTO;
import com.healthcaremanagement.service.entity.TimeSlot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TimeSlotMapping {
    TimeSlotMapping INSTANCE = Mappers.getMapper(TimeSlotMapping.class);


    TimeSlotDTO mapToDto(TimeSlot source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "availability", ignore = true)
    TimeSlot mapToEntity(TimeSlotDTO source);
}
