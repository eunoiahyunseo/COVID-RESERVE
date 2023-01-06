package com.hyunseo.covidreserve.dto;

import com.hyunseo.covidreserve.constant.PlaceType;
import com.hyunseo.covidreserve.domain.Place;

/**
 * @author ihyeonseo
 */
public record PlaceDTO(
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        Integer capacity,
        String memo
){
    public static PlaceDTO of(
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ) {
        return new PlaceDTO(placeType, placeName, address, phoneNumber, capacity, memo);
    }
}
