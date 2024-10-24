package com.hhplus.concert.app.common.exception;

public enum ErrorCode {
    BAD_REQUEST("400", "Bad Request"),
    UNAUTHORIZED("401", "Unauthorized"),
    NOT_FOUND("404", "Not Found"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error");

    private final String code;
    private final String description;

    ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}