package com.hyunseo.covidreserve.controller.error;

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
import org.springframework.data.rest.webmvc.RepositoryRestController;


/**
 * @author ihyeonseo
 */

// + spring data rest - controller
@RestControllerAdvice(annotations = {RestController.class, RepositoryRestController.class})
public class APIExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<Object> validation(ConstraintViolationException e, WebRequest request) {
        return handleExceptionInternal(e, ErrorCode.VALIDATION_ERROR, request);
    }

    @ExceptionHandler
    public ResponseEntity<Object> general(GeneralException e, WebRequest request) {
        return handleExceptionInternal(e, e.getErrorCode(), request);
    }


    @ExceptionHandler
    public ResponseEntity<Object> exception(Exception e, WebRequest request) {
        return handleExceptionInternal(e, ErrorCode.INTERNAL_ERROR, request);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {
        return handleExceptionInternal(ex, ErrorCode.valueOf(statusCode), headers, statusCode, request);
    }

    private ResponseEntity<Object> handleExceptionInternal(Exception e, ErrorCode errorCode, WebRequest request) {
        return handleExceptionInternal(e, errorCode, HttpHeaders.EMPTY, errorCode.getHttpStatusCode(), request);
    }

        private ResponseEntity<Object> handleExceptionInternal (Exception e,
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
