package com.icodeshivam.blacklisting.model;

public enum ErrorCodes {

    INVALID_CIDR_NOTATION(1001, "Invalid CIDR Notation"),
    INVALID_MASK_NOTATION(1002, "Invalid Mask Notation"),
    INVALID_IP(1003, "Invalid IP Address"),

    INTERNAL_SERVER_ERROR(9999, "Internal Server Error");

    private Integer errorCode;
    private String errorMessage;

    ErrorCodes(Integer errorCode, String errorMessage) {
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

