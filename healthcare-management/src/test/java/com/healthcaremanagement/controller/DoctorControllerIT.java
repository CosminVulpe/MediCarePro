package com.healthcaremanagement.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.healthcaremanagement.controller.dto.DoctorDTO;
import com.healthcaremanagement.controller.dto.DoctorIdResponse;
import com.healthcaremanagement.controller.dto.PatienceIdRequest;
import com.healthcaremanagement.service.entity.Doctor;
import com.healthcaremanagement.service.repository.DoctorRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Collection;
import java.util.List;

import static com.healthcaremanagement.utils.TestDummy.getAllDoctorsMock;
import static com.healthcaremanagement.utils.UtilsMock.readValue;
import static com.healthcaremanagement.utils.UtilsMock.writeJsonAsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test-memory")
public class DoctorControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DoctorRepository doctorRepository;

    private static final String API_HEALTHCARE = "/api/healthcare";

    @BeforeEach
    void init() {
        doctorRepository.saveAll(getAllDoctorsMock());
    }

    @Test
    void getAllDoctors() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(API_HEALTHCARE)
                .accept(APPLICATION_JSON);

        String contentAsString = mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<DoctorDTO> doctorDTOS = readValue(contentAsString, new TypeReference<>() {
        });

        assertThat(doctorDTOS).isNotEmpty();
        doctorDTOS.forEach(doctor -> {
            assertThat(doctor.name()).isNotBlank();
            assertThat(doctor.availabilityList()).isNotEmpty();
            assertThat(doctor.contactInfo()).isNotNull();
            assertThat(doctor.specialty()).isNotNull();
        });
    }

    @Test
    void assignPatienceId() throws Exception {
        long patienceId = 10L;
        PatienceIdRequest request = new PatienceIdRequest(List.of("John", "Smith"), patienceId);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post(API_HEALTHCARE + "/assign")
                .accept(APPLICATION_JSON)
                .content(writeJsonAsString(request))
                .contentType(APPLICATION_JSON);

        String contentAsString = mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        DoctorIdResponse doctorIdResponse = readValue(contentAsString, DoctorIdResponse.class);

        assertThat(doctorIdResponse).isNotNull();

        List<Doctor> doctorList = request.doctorNames().stream().map(doctorRepository::findDoctorsByName)
                .flatMap(Collection::stream).toList();

        doctorList.forEach(doctor -> assertThat(doctor.getPatienceIds()).contains(patienceId));
    }

    @Test
    void assignPatienceIdWhenDoctorIsNotFound() throws Exception {
        long patienceId = 10L;
        PatienceIdRequest request = new PatienceIdRequest(List.of("Cosmin"), patienceId);

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post(API_HEALTHCARE + "/assign")
                .accept(APPLICATION_JSON)
                .content(writeJsonAsString(request))
                .contentType(APPLICATION_JSON);
        mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isNoContent());
    }
}
