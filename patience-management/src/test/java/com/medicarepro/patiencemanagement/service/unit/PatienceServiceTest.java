package com.medicarepro.patiencemanagement.service.unit;

import com.medicarepro.patiencemanagement.controller.dto.PatienceDTO;
import com.medicarepro.patiencemanagement.service.PatienceService;
import com.medicarepro.patiencemanagement.service.exception.PatienceIdException;
import com.medicarepro.patiencemanagement.service.repository.PatienceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.medicarepro.patiencemanagement.utils.TestDummy.getAllPatience;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatienceServiceTest {
    @Mock
    private PatienceRepository patienceRepository;

    @InjectMocks
    private PatienceService patienceService;

    private static final long ID = 10L;


    @Test
    void shouldGetAllPatience() {
        when(patienceRepository.findAll()).thenReturn(getAllPatience());

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

        doThrow(new PatienceIdException(errorMessage)).when(patienceRepository)
                .findById(any());

        assertThatThrownBy(() -> patienceService.findPatienceById(ID))
                .isInstanceOf(PatienceIdException.class)
                .hasMessage(errorMessage);
    }


    @Test
    void shouldFindPatienceById() {
        when(patienceRepository.findById(any())).thenReturn(Optional.ofNullable(getAllPatience().get(0)));

        PatienceDTO patienceById = patienceService.findPatienceById(ID);
        assertThat(patienceById).isNotNull();
    }

}
