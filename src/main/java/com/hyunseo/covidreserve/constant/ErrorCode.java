package com.hyunseo.covidreserve.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * @author ihyeonseo
 * @created 05/01/2023 - 10:16 PM
 */

/**
 * 에러를 미리 정의함으로써, 대 내외적으로 이를 다루기 용이하게 함으로써 프로젝트 안정성을 증가시켜주기 위해 설계
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    OK(0, HttpStatus.OK, "Ok"),

    BAD_REQUEST(10000, HttpStatus.BAD_REQUEST, "bad request"),
    SPRING_BAD_REQUEST(10001, HttpStatus.BAD_REQUEST, "spring-detected bad request"),
    VALIDATION_ERROR(10002, HttpStatus.BAD_REQUEST, "Validation error"),
    NOT_FOUND(10003, HttpStatus.NOT_FOUND, "Requested resource is not found"),

    INTERNAL_ERROR(20000, HttpStatus.INTERNAL_SERVER_ERROR, "internal error"),
    SPRING_INTERNAL_ERROR(20001, HttpStatus.INTERNAL_SERVER_ERROR, "spring-detected internal error"),
    DATA_ACCESS_ERROR(20002, HttpStatus.INTERNAL_SERVER_ERROR, "Data access error");

    // 인자로 HttpStatus.BAD_REQUEST -> ErrorCode.BAD_REQUEST 반환
    public static ErrorCode valueOf(HttpStatusCode httpStatusCode) {
        return Arrays.stream(values())
                .filter(errorCode -> errorCode.getHttpStatusCode() == httpStatusCode)
                .findFirst()
                .orElseGet(() -> {
                    if(httpStatusCode.is4xxClientError()) { return ErrorCode.BAD_REQUEST;}
                    else if(httpStatusCode.is5xxServerError()) {return ErrorCode.INTERNAL_ERROR;}
                    else {return ErrorCode.OK; }
                });
    }

    private final Integer code;
    private final HttpStatusCode httpStatusCode;
    private final String message;

    public String getMessage(Throwable e) {
        return getMessage(this.getMessage() + " - " + e.getMessage());
    }

    public String getMessage(String message) {
        return Optional.ofNullable(message)
                .filter(Predicate.not(String::isBlank))
                .orElse(this.getMessage());

    }

    @Override
    public String toString() {
        return String.format("%s (%d)", this.name(), this.getCode());
    }
}
