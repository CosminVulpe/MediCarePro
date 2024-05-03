package com.medicarepro.patiencemanagement.service;

import com.medicarepro.patiencemanagement.controller.dto.PatienceDTO;
import com.medicarepro.patiencemanagement.service.entity.Patience;
import com.medicarepro.patiencemanagement.service.exception.PatienceIdException;
import com.medicarepro.patiencemanagement.service.repository.PatienceRepository;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static com.medicarepro.patiencemanagement.utils.TestDummy.getAllPatiences;
import static com.medicarepro.patiencemanagement.utils.TestDummy.getPatienceReqMock;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PatienceServiceTest {
    private static final long ID = 10L;

    @Mock
    private PatienceRepository patienceRepository;

    @InjectMocks
    private PatienceService patienceService;

    @Test
    void shouldGetAllPatience() {
        when(patienceRepository.findAll()).thenReturn(getAllPatiences());

        List<PatienceDTO> patienceDTOS = patienceService.getAll();
        assertThat(patienceDTOS).isNotNull();
        patienceDTOS.forEach(patienceDTO -> {
            assertThat(patienceDTO.patienceId()).isNotBlank();
            assertThat(patienceDTO.contractInformationDTO()).isNotNull();
            assertThat(patienceDTO.demographicInformationDTO()).isNotNull();
            assertThat(patienceDTO.insuranceInformationDTO()).isNotNull();
            assertThat(patienceDTO.medicalHistoryDTO()).isNotNull();
        });
    }

    @Test
    void shouldThrowExceptionWhenIdDoesNotExist() {
        String errorMessage = "Patience with ID cannot be found";

        doThrow(new PatienceIdException(errorMessage)).when(patienceRepository).findById(any());
        assertThatThrownBy(() -> patienceService.findPatienceById(ID)).isInstanceOf(PatienceIdException.class).hasMessage(errorMessage);
    }

    @Test
    void shouldFindPatienceById() {
        when(patienceRepository.findById(any())).thenReturn(getOptionalPatience());

        PatienceDTO patienceById = patienceService.findPatienceById(ID);
        assertThat(patienceById).isNotNull();
        verify(patienceRepository).findById(any());
    }

    @Test
    void createPatienceSuccessfully() {
        Patience patience = getOptionalPatience().get();
        when(patienceRepository.save(any())).thenReturn(patience);

        ResponseEntity<PatienceDTO> response = patienceService.createPatience(getPatienceReqMock());
        assertThat(response.getBody()).isNotNull();
        verify(patienceRepository).save(any());
    }


    @Test
    void shouldThrowExceptionWhenAddingANewPatience() {
        doThrow(new PersistenceException()).when(patienceRepository).save(any());

        assertThatThrownBy(() -> patienceService.createPatience(getPatienceReqMock())).isInstanceOf(PersistenceException.class);
    }

    @Test
    void updatePatienceSuccessfully() {
        when(patienceRepository.findById(any())).thenReturn(getOptionalPatience());

        patienceService.updatePatience(ID, getPatienceReqMock());
        verify(patienceRepository).save(any());
    }

    @Test
    void deletePatienceSuccessfully() {
        when(patienceRepository.findById(any())).thenReturn(getOptionalPatience());

        patienceService.deletePatienceById(ID);
        verify(patienceRepository).delete(any());
    }


    private Optional<Patience> getOptionalPatience() {
        return Optional.of(getAllPatiences().get(0));
    }

}
