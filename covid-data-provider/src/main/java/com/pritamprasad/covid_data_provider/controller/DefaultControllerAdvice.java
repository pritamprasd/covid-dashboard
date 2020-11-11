package com.pritamprasad.covid_data_provider.controller;

import com.pritamprasad.covid_data_provider.exception.BadRequestException;
import com.pritamprasad.covid_data_provider.exception.CovidDataProviderException;
import com.pritamprasad.covid_data_provider.exception.EntityNotFoundException;
import com.pritamprasad.covid_data_provider.exception.InternalErrorException;
import com.pritamprasad.covid_data_provider.models.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice()
public class DefaultControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoStateFoundException(EntityNotFoundException ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.toString());
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.toString());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ErrorResponse> handleInternalErrorException(InternalErrorException ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.toString());
        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }

    /**
     * Keep this handler at bottom, just before Exception/Throwable handler (if-any)
     * @param ex CovidDataProviderException instance
     * @param request web request
     * @return INTERNAL_SERVER_ERROR response
     */
    @ExceptionHandler(CovidDataProviderException.class)
    public ResponseEntity<ErrorResponse> handleBaseException(CovidDataProviderException ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.toString());
        return new ResponseEntity<>(errorResponse, INTERNAL_SERVER_ERROR);
    }
}
