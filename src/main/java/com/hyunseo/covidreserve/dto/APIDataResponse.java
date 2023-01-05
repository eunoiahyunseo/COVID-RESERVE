package com.hyunseo.covidreserve.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author ihyeonseo
 * @created 05/01/2023 - 10:55 PM
 */
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class APIDataResponse extends com.hyunseo.covidreserve.dto.APIErrorResponse {

    private final Object data;

    private APIDataResponse(boolean success, Integer errorCode, String message, Object data) {
        super(success, errorCode, message);
        this.data = data;
    }

    public static APIDataResponse of(boolean success, Integer errorCode, String message, Object data) {
        return new APIDataResponse(success, errorCode, message, data);
    }
}
