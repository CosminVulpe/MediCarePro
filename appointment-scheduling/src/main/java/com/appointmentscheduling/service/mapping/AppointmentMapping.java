package com.appointmentscheduling.service.mapping;

import com.appointmentscheduling.controller.dto.AppointmentResponse;
import com.appointmentscheduling.controller.dto.AppointmentSchedulerRequest;
import com.appointmentscheduling.service.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AppointmentMapping {
    AppointmentMapping INSTANCE = Mappers.getMapper(AppointmentMapping.class);

    AppointmentResponse mapToDto(Appointment source);

    Appointment mapToEntity(AppointmentSchedulerRequest request);
}
