package com.hyunseo.covidreserve.domain;

import com.hyunseo.covidreserve.constant.PlaceType;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ihyeonseo
 * @created 05/01/2023 - 9:15 PM
 */
@Data
public class Place {
    private Long id;

    private PlaceType placeType;
    private String placeName;
    private String address;
    private String phoneNumber;
    private Integer capacity;
    private String memo;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}