package com.medicarepro.patiencemanagement.service;

import com.medicarepro.patiencemanagement.controller.dto.DoctorIdResponse;
import com.medicarepro.patiencemanagement.controller.dto.PatienceDTO;
import com.medicarepro.patiencemanagement.controller.dto.ScheduleAppointmentRequest;
import com.medicarepro.patiencemanagement.service.entity.Patience;
import com.medicarepro.patiencemanagement.service.exception.PatienceIdException;
import com.medicarepro.patiencemanagement.service.repository.AppointmentProxyClient;
import com.medicarepro.patiencemanagement.service.repository.HealthcareProxyClient;
import com.medicarepro.patiencemanagement.service.repository.PatienceRepository;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static com.medicarepro.patiencemanagement.utils.TestDummy.getAllPatients;
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
    @Mock
    private HealthcareProxyClient healthcareProxyClient;
    @Mock
    private AppointmentProxyClient appointmentProxyClient;

    @InjectMocks
    private PatienceService patienceService;

    @Test
    void shouldGetAllPatience() {
        when(patienceRepository.findAll()).thenReturn(getAllPatients());

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
        patience.setId(1L);

        ResponseEntity<DoctorIdResponse> doctorIdResponseResponseEntity = new ResponseEntity<>(new DoctorIdResponse(List.of(1L)), HttpStatus.OK);

        when(patienceRepository.save(any())).thenReturn(patience);
        when(healthcareProxyClient.assignPatience(any())).thenReturn(doctorIdResponseResponseEntity);

        ResponseEntity<PatienceDTO> response = patienceService.createPatience(getPatienceReqMock());
        assertThat(response.getBody()).isNotNull();
        verify(patienceRepository, times(2)).save(any());
    }

    @Test
    void shouldThrowExceptionWhenAddingANewPatience() {
        when(patienceRepository.doesPatienceExist(any())).thenReturn(0);
        doThrow(new PersistenceException()).when(patienceRepository).save(any());
        assertThatThrownBy(() -> patienceService.createPatience(getPatienceReqMock()))
                .isInstanceOf(PersistenceException.class);
    }

    @Test
    void createNewPatienceReturnConflict() {
        when(patienceRepository.doesPatienceExist(any())).thenReturn(1);
        ResponseEntity<PatienceDTO> response = patienceService.createPatience(getPatienceReqMock());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
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

    @Test
    void scheduleAppointment() {
        ScheduleAppointmentRequest request = new ScheduleAppointmentRequest(LocalTime.of(13, 30, 0), DayOfWeek.SATURDAY, "", 1L, 2L);
        when(healthcareProxyClient.checkAvailability(any())).thenReturn(Boolean.TRUE);

        when(appointmentProxyClient.scheduleAppointment(any())).thenReturn(
                new ResponseEntity<>(HttpStatus.OK)
        );

        ResponseEntity<String> stringResponseEntity = patienceService.scheduleAppoint(request);

        assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(stringResponseEntity.getBody()).isEqualTo("Appointment scheduled successfully at " + request.time() + " day of the week " + request.dayOfWeek());
    }

    @Test
    void scheduleAppointmentThrowsException() {
        ScheduleAppointmentRequest request = new ScheduleAppointmentRequest(LocalTime.of(13, 30, 0), DayOfWeek.SATURDAY, "", 1L, 2L);
        when(healthcareProxyClient.checkAvailability(any())).thenReturn(Boolean.TRUE);

        when(appointmentProxyClient.scheduleAppointment(any())).thenReturn(
                new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR)
        );

        assertThatThrownBy(() -> patienceService.scheduleAppoint(request))
                .isInstanceOf(RestClientException.class)
                .hasMessage("Unable to schedule appointment");
    }

    @Test
    void scheduleAppointmentNoAvailability() {
        ScheduleAppointmentRequest request = new ScheduleAppointmentRequest(LocalTime.of(13, 30, 0), DayOfWeek.SATURDAY, "", 1L, 2L);
        when(healthcareProxyClient.checkAvailability(any())).thenReturn(Boolean.FALSE);
        ResponseEntity<String> stringResponseEntity = patienceService.scheduleAppoint(request);

        assertThat(stringResponseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(stringResponseEntity.getBody()).isEqualTo("No availability");
    }


    private Optional<Patience> getOptionalPatience() {
        return Optional.of(getAllPatients().get(0));
    }

}
