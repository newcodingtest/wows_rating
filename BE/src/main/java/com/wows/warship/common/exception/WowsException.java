package com.wows.warship.common.exception;

import org.springframework.http.HttpStatus;

public class WowsException extends RuntimeException {

    private final HttpStatus status;
    private final ErrorCode errorCode;

    public WowsException(final HttpStatus status, final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.status = status;
        this.errorCode = errorCode;
    }

    public static class NotFoundUserException extends WowsException {
        public NotFoundUserException(final WowsErrorCode errorCode, final String parameter) {
            super(errorCode.getStatus(), new ErrorCode<>(errorCode.getCode(), errorCode.getMessage(), parameter));
        }
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
