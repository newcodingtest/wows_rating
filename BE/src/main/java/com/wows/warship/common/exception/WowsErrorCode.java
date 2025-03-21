package com.wows.warship.common.exception;

import org.springframework.http.HttpStatus;

public enum WowsErrorCode {
    UNKNOWN_SERVER_ERROR_CODE(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 에러입니다.", "0000"),
    REQUEST_VALID_ERROR_CODE(HttpStatus.BAD_REQUEST, "요청을 다시 확인해주세요.", "0001"),
    NOT_FOUNT_USER(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다.", "0002"),
            ;
    private final HttpStatus status;
    private final String message;
    private final String code;

    WowsErrorCode(final HttpStatus status, final String message, final String code) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
