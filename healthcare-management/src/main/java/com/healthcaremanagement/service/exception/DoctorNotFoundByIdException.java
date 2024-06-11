package com.healthcaremanagement.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class DoctorNotFoundByIdException extends RuntimeException {
    public DoctorNotFoundByIdException(String message) {
        super(message);
        log.error(message);
    }
}
