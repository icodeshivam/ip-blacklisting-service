package com.icodeshivam.blacklisting.exception;

import com.icodeshivam.blacklisting.model.ErrorCodes;
import com.icodeshivam.blacklisting.model.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(ValidationException ex) {
        LOGGER.error("Error while handling request, ERROR: {}", ex.getMessage(), ex);
        return new ErrorResponse(ex.getErrorCode(), ex.getErrorMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception ex) {
        LOGGER.error("Error while handling request, ERROR: {}", ex.getMessage(), ex);
        return new ErrorResponse(ErrorCodes.INTERNAL_SERVER_ERROR.getErrorCode(), ErrorCodes.INTERNAL_SERVER_ERROR.getErrorMessage());
    }
}
