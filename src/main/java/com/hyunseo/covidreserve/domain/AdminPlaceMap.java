package com.hyunseo.covidreserve.domain;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * @author ihyeonseo
 * @created 05/01/2023 - 9:16 PM
 */

@Data
public class AdminPlaceMap {
    private Long id;

    private Long adminId;
    private Long PlaceId;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}