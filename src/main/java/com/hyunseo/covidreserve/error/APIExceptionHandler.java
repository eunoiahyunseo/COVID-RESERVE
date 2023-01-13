package com.hyunseo.covidreserve.error;

import com.hyunseo.covidreserve.constant.ErrorCode;
import com.hyunseo.covidreserve.dto.APIErrorResponse;
import com.hyunseo.covidreserve.exception.GeneralException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * @author ihyeonseo
 */

@RestControllerAdvice(annotations = RestController.class)
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        return getInternalResponseEntity(
                e, ErrorCode.VALIDATION_ERROR, HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, request
        );
    }


    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus status = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;
        return getInternalResponseEntity(e, errorCode, HttpHeaders.EMPTY, status, request);

    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        return getInternalResponseEntity(
                e, ErrorCode.INTERNAL_ERROR, HttpHeaders.EMPTY, HttpStatus.INTERNAL_SERVER_ERROR, request
        );
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {
        ErrorCode errorCode = statusCode.is4xxClientError() ?
                ErrorCode.SPRING_BAD_REQUEST :
                ErrorCode.SPRING_INTERNAL_ERROR;

        return getInternalResponseEntity(ex, errorCode, HttpHeaders.EMPTY, statusCode, request);
    }

    private ResponseEntity<Object> getInternalResponseEntity (Exception e,
                                                              ErrorCode errorCode,
                                                              HttpHeaders headers,
                                                              HttpStatusCode statusCode,
                                                              WebRequest request
    ) {
        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(
                        false, errorCode.getCode(), errorCode.getMessage(e)
                ),
                headers,
                statusCode,
                request
        );
    }
}
