package com.medicarepro.patiencemanagement.service.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@ResponseStatus(code = NOT_FOUND)
public class PatienceIdException extends RuntimeException {

    public PatienceIdException(String message) {
        super(message);
        log.error(message);
    }
}
