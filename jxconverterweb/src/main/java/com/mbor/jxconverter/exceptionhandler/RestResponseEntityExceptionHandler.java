package com.mbor.jxconverter.exceptionhandler;

import com.mbor.converterservice.exception.ProcessingException;
import com.mbor.jxconverter.model.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = ProcessingException.class)
    public final ResponseEntity<ApiError> handleProcessingException(ProcessingException processingException, WebRequest webRequest){

        return new ResponseEntity<>(new ApiError(processingException.getMessage()), HttpStatus.BAD_REQUEST);
    }

}
