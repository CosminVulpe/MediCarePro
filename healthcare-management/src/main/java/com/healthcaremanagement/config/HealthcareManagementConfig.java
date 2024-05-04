package com.healthcaremanagement.config;

import com.healthcaremanagement.service.entity.*;
import com.healthcaremanagement.service.repository.DoctorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;

@Configuration
public class HealthcareManagementConfig {
    private static final Random RANDOM = new Random();
    private static final DoctorSpecialty[] DOCTOR_SPECIALTIES = DoctorSpecialty.values();
    private static final DayOfWeek[] DAY_OF_WEEKS = DayOfWeek.values();

    @Bean
    CommandLineRunner commandLineRunner(DoctorRepository doctorRepository) {
        return args -> {
            Doctor johnSmith = createDoctor("Dr. John Smith", "johnsmith@yahoo.com", "123 Main St", "0985123456");
            Doctor emilyJohnson = createDoctor("Dr. Emily Johnson", "emilyjohnson@yahoo.com", "456 Elm St", "0985234567");
            Doctor michaelWilliams = createDoctor("Dr. Michael Williams", "michaelwilliams@yahoo.com", "789 Oak St", "0985345678");
            Doctor sarahBrown = createDoctor("Dr. Sarah Brown", "sarahbrown@yahoo.com", "1011 Pine St", "0985456789");
            Doctor christopherLee = createDoctor("Dr. Christopher Lee", "christopherlee@yahoo.com", "1213 Cedar St", "0985567890");

            doctorRepository.saveAll(List.of(johnSmith, emilyJohnson, michaelWilliams, sarahBrown, christopherLee));
        };

    }

    private static Doctor createDoctor(String name, String email, String address, String phoneNumber) {
        Doctor doctor = Doctor.builder()
                .name(name)
                .specialty(DOCTOR_SPECIALTIES[RANDOM.nextInt(DOCTOR_SPECIALTIES.length)])
                .build();

        ContactInfo contactInfo = ContactInfo.builder()
                .address(address)
                .email(email)
                .phoneNumber(phoneNumber)
                .doctor(doctor)
                .build();

        Availability availability = Availability.builder()
                .dayOfWeek(DAY_OF_WEEKS[(int) (Math.random() * DAY_OF_WEEKS.length)])
                .doctor(doctor)
                .build();

        TimeSlot timeSlot = TimeSlot.builder()
                .startTime(LocalTime.of(9 + (int) (Math.random() * 10), 30))
                .endTime(LocalTime.of(12 + (int) (Math.random() * 9), 30))
                .availability(availability)
                .build();

        List<Availability> slotsAvailability = List.of(availability);
        availability.setTimeSlot(timeSlot);

        doctor.setContactInfo(contactInfo);
        doctor.setAvailabilityList(slotsAvailability);

        return doctor;
    }

}
