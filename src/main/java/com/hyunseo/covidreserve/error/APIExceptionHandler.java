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
        ErrorCode errorCode = ErrorCode.VALIDATION_ERROR;
        HttpStatus statusCode = HttpStatus.BAD_REQUEST;

        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(
                        false, errorCode.getCode(), errorCode.getMessage(e)
                ),
                HttpHeaders.EMPTY,
                statusCode,
                request
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        ErrorCode errorCode = e.getErrorCode();
        HttpStatus statusCode = errorCode.isClientSideError() ?
                HttpStatus.BAD_REQUEST :
                HttpStatus.INTERNAL_SERVER_ERROR;

        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(
                        false, errorCode.getCode(), errorCode.getMessage(e)
                ),
                HttpHeaders.EMPTY,
                statusCode,
                request
        );
    }

    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        ErrorCode errorCode = ErrorCode.INTERNAL_ERROR;
        HttpStatus statusCode = HttpStatus.INTERNAL_SERVER_ERROR;

        return super.handleExceptionInternal(
                e,
                APIErrorResponse.of(
                        false, errorCode.getCode(), errorCode.getMessage(e)
                ),
                HttpHeaders.EMPTY,
                statusCode,
                request
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

        return super.handleExceptionInternal(
                ex,
                APIErrorResponse.of(
                        false, errorCode.getCode(), errorCode.getMessage(ex)
                ),
                headers,
                statusCode,
                request
         );
    }
}
