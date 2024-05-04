package com.healthcaremanagement.service.mapping;

import com.healthcaremanagement.controller.dto.AvailabilityDTO;
import com.healthcaremanagement.service.entity.Availability;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = TimeSlotMapping.class)
public interface AvailabilityMapping {
    AvailabilityMapping INSTANCE = Mappers.getMapper(AvailabilityMapping.class);

    AvailabilityDTO mapToDto(Availability source);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    @Mapping(target = "timeSlot", ignore = true)
    Availability mapToEntity(AvailabilityDTO source);

}
