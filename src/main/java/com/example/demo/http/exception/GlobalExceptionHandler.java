package com.example.demo.http.exception;

import com.example.demo.concept.exceptions.ParticipantNotAuthorized;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ParticipantNotAuthorized.class)
    public ResponseEntity<Object> handleParticipantNotAuthorized(ParticipantNotAuthorized participantNotAuthorized, WebRequest webRequest) {
        HttpStatusCode statusCode = HttpStatusCode.valueOf(HttpStatus.UNAUTHORIZED.value());
        ErrorResponse errorResponse = ErrorResponse.builder(participantNotAuthorized, statusCode, "E001").build();
        return handleExceptionInternal(participantNotAuthorized, errorResponse, new HttpHeaders(), statusCode, webRequest);
    }
}
