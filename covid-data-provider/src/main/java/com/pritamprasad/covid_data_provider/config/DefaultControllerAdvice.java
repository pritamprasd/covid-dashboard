package com.pritamprasad.covid_data_provider.config;

import com.pritamprasad.covid_data_provider.exception.BadRequestException;
import com.pritamprasad.covid_data_provider.exception.CovidDataProviderException;
import com.pritamprasad.covid_data_provider.exception.EntityNotFoundException;
import com.pritamprasad.covid_data_provider.models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice()
public class DefaultControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoStateFoundException(EntityNotFoundException ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException ex, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorMessage(ex.toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
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
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
