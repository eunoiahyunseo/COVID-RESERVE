package com.hyunseo.covidreserve.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author ihyeonseo
 * @created 05/01/2023 - 10:55 PM
 */

/**
 * json body에서 일정한 형식을 유지하기 위해 검사가 필요하지 않게끔 만든다.
 *
 */

@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class APIDataResponse extends com.hyunseo.covidreserve.dto.APIErrorResponse {
// APIErrorResponse안의 필드도 동일성 검사를 해야하기 때문 -> EqualsAndHashCode(callSuper = true)
    private final Object data;

    private APIDataResponse(boolean success, Integer errorCode, String message, Object data) {
        super(success, errorCode, message);
        this.data = data;
    }

    public static APIDataResponse of(boolean success, Integer errorCode, String message, Object data) {
        return new APIDataResponse(success, errorCode, message, data);
    }
}
