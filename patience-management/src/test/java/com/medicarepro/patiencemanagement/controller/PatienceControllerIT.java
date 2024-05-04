package com.medicarepro.patiencemanagement.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.medicarepro.patiencemanagement.controller.dto.ContractInformationDTO;
import com.medicarepro.patiencemanagement.controller.dto.PatienceDTO;
import com.medicarepro.patiencemanagement.controller.dto.PatienceRequest;
import com.medicarepro.patiencemanagement.service.entity.ContractInformation;
import com.medicarepro.patiencemanagement.service.entity.Patience;
import com.medicarepro.patiencemanagement.service.repository.PatienceRepository;
import com.medicarepro.patiencemanagement.utils.TestDummy;
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

import java.util.List;
import java.util.Optional;

import static com.medicarepro.patiencemanagement.utils.TestDummy.getPatienceReqMock;
import static com.medicarepro.patiencemanagement.utils.UtilsMock.readValue;
import static com.medicarepro.patiencemanagement.utils.UtilsMock.writeJsonAsString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test-memory")
public class PatienceControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PatienceRepository repository;

    private static final String API_PATIENCE = "/api/patience";

    @BeforeEach
    void init() {
        repository.saveAll(TestDummy.getAllPatiences());
    }

    @Test
    void getAllPatiences() throws Exception {
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = getMockHttpServletRequestBuilder(null);

        String contentAsString = mockMvc.perform(mockHttpServletRequestBuilder)
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<PatienceDTO> patienceResponse = readValue(contentAsString, new TypeReference<>() {
        });

        assertThat(patienceResponse).isNotNull();
        patienceResponse.forEach(patience -> {
            assertThat(patience.patienceId()).isNotBlank();
            assertThat(patience.demographicInformationDTO()).isNotNull();
            assertThat(patience.contractInformationDTO()).isNotNull();
            assertThat(patience.insuranceInformationDTO()).isNotNull();
            assertThat(patience.medicalHistoryDTO()).isNotNull();
        });
    }

    @Test
    void getPatienceById() throws Exception {
        Patience patience = getPatience(0);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = getMockHttpServletRequestBuilder(patience.getId());

        String contentAsString = mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        PatienceDTO patienceDTO = readValue(contentAsString, PatienceDTO.class);
        assertThat(patienceDTO).isNotNull();
    }


    @Test
    void createNewPatience() throws Exception {
        PatienceRequest patienceReqMock = getPatienceReqMock();
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post(API_PATIENCE + "/create")
                .accept(APPLICATION_JSON)
                .content(writeJsonAsString(patienceReqMock))
                .contentType(APPLICATION_JSON);

        String contentAsString = mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        PatienceDTO patienceDTO = readValue(contentAsString, PatienceDTO.class);
        assertThat(patienceDTO).isNotNull();

        Optional<Patience> optionalPatience = repository.findAll().stream().filter(patience -> patience.getPatienceId().equals(patienceDTO.patienceId()))
                .findFirst();
        assertThat(optionalPatience).isPresent();
    }

    @Test
    void updatePatience() throws Exception {
        Patience patience = getPatience(1);
        String originalPhoneNumber = patience.getContractInformation().getPhoneNumber();

        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.patch(API_PATIENCE + "/update/{patienceId}", patience.getId())
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .content(writeJsonAsString(computeUpdatePatienceReq()));

        mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk());

        String updatedPhoneNumber = repository.findById(patience.getId())
                .map(Patience::getContractInformation)
                .map(ContractInformation::getPhoneNumber).orElse("");

        assertThat(updatedPhoneNumber).isNotBlank();
        assertThat(originalPhoneNumber).isNotEqualTo(updatedPhoneNumber);
    }

    @Test
    void deletePatience() throws Exception {
        Patience patience = getPatience(3);
        MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.delete(API_PATIENCE + "/delete/{patienceId}", patience.getId())
                .accept(APPLICATION_JSON);

        mockMvc.perform(mockHttpServletRequestBuilder).andExpect(status().isOk());

        Optional<Patience> patienceOptional = repository.findById(patience.getId());
        assertThat(patienceOptional).isEmpty();
    }


    private MockHttpServletRequestBuilder getMockHttpServletRequestBuilder(Long params) {
        if (params == null) {
            return MockMvcRequestBuilders.get(API_PATIENCE)
                    .accept(APPLICATION_JSON);
        }
        return MockMvcRequestBuilders.get(API_PATIENCE + "/{patienceId}", params)
                .accept(APPLICATION_JSON);
    }

    private PatienceRequest computeUpdatePatienceReq() {
        PatienceRequest patienceReqMock = getPatienceReqMock();

        ContractInformationDTO contractInformation = patienceReqMock.contractInformationRequest();
        ContractInformationDTO contractInformationDTO = new ContractInformationDTO(contractInformation.address(), "074929375897234", contractInformation.email());

        return new PatienceRequest(contractInformationDTO, patienceReqMock.demographicInformationRequest(), patienceReqMock.insuranceInformationRequest(), patienceReqMock.medicalHistoryRequest(),"John");
    }

    private Patience getPatience(int i) {
        return repository.findAll().get(i);
    }
}
