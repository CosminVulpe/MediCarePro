package com.healthcaremanagement.service;

import com.healthcaremanagement.controller.dto.DoctorDTO;
import com.healthcaremanagement.controller.dto.DoctorIdResponse;
import com.healthcaremanagement.controller.dto.PatienceIdRequest;
import com.healthcaremanagement.service.entity.Doctor;
import com.healthcaremanagement.service.repository.DoctorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import static com.healthcaremanagement.utils.TestDummy.getAllDoctorsMock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {
    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    @Test
    void shouldGetAllDoctors() {
        when(doctorRepository.findAll()).thenReturn(getAllDoctorsMock());

        List<DoctorDTO> allDoctors = doctorService.getAllDoctors();
        assertThat(allDoctors).isNotNull();
        allDoctors.forEach(doctor -> {
            assertThat(doctor.name()).isNotBlank();
            assertThat(doctor.availabilityList()).isNotEmpty();
        });
    }

    @Test
    void assignPatienceToDoctor() {
        when(doctorRepository.findDoctorsByName(any())).thenReturn(getAllDoctorsMock());

        PatienceIdRequest request = new PatienceIdRequest(
                getAllDoctorsMock().stream().map(Doctor::getName).limit(2).collect(Collectors.toList())
                , 10L
        );
        ResponseEntity<DoctorIdResponse> doctorIdResponseResponseEntity = doctorService.assignPatience(request);

        assertThat(doctorIdResponseResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(doctorIdResponseResponseEntity.getBody()).isNotNull();
    }

}
