package com.healthcaremanagement.utils;

import com.healthcaremanagement.service.entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TestDummy {
    private static final Random RANDOM = new Random();
    private static final DoctorSpecialty[] DOCTOR_SPECIALTIES = DoctorSpecialty.values();

    private TestDummy() {
    }

    public static List<Doctor> getAllDoctorsMock() {
        Doctor johnSmith = createDoctor("Dr. John Smith", "johnsmith@yahoo.com"
                , "123 Main St", "0985123456", List.of(1L));
        Doctor emilyJohnson = createDoctor("Dr. Emily Johnson", "emilyjohnson@yahoo.com"
                , "456 Elm St", "0985234567", List.of(2L));
        Doctor michaelWilliams = createDoctor("Dr. Michael Williams", "michaelwilliams@yahoo.com"
                , "789 Oak St", "0985345678", List.of(3L));
        Doctor sarahBrown = createDoctor("Dr. Sarah Brown", "sarahbrown@yahoo.com"
                , "1011 Pine St", "0985456789", List.of(4L));
        Doctor christopherLee = createDoctor("Dr. Christopher Lee", "christopherlee@yahoo.com"
                , "1213 Cedar St", "0985567890", List.of(5L));
        return List.of(johnSmith, emilyJohnson, michaelWilliams, sarahBrown, christopherLee);
    }

    private static Doctor createDoctor(String name, String email, String address, String phoneNumber, List<Long> patienceIds) {
        Doctor doctor = Doctor.builder()
                .name(name)
                .specialty(DOCTOR_SPECIALTIES[RANDOM.nextInt(DOCTOR_SPECIALTIES.length)])
                .patienceIds(new ArrayList<>(patienceIds))
                .build();

        ContactInfo contactInfo = ContactInfo.builder()
                .address(address)
                .email(email)
                .phoneNumber(phoneNumber)
                .doctor(doctor)
                .build();

        LocalDate date = LocalDate.now().plusDays(RANDOM.nextInt(365) + 1);
        Availability availability = Availability.builder()
                .dayOfWeek(date.getDayOfWeek())
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
