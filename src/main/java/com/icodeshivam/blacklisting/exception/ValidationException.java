package com.icodeshivam.blacklisting.exception;

import com.icodeshivam.blacklisting.model.ErrorCodes;

public class ValidationException extends RuntimeException {
    private Integer errorCode;
    private String errorMessage;

    public ValidationException(ErrorCodes errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode.getErrorCode();
        this.errorMessage = errorCode.getErrorMessage();
    }

    public ValidationException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
