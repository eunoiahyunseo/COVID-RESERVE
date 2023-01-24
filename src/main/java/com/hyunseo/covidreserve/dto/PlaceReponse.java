package com.hyunseo.covidreserve.dto;

import com.hyunseo.covidreserve.constant.PlaceType;

/**
 * @author ihyeonseo
 */
public record PlaceReponse(
        Long id,
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        Integer capacity,
        String memo
) {
    public static PlaceReponse of(
            Long id,
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ){
        return new PlaceReponse(id, placeType, placeName, address, phoneNumber, capacity, memo);
    }

    public static PlaceReponse from(PlaceDto placeDto) {
        if(placeDto == null) {return null;}
        return PlaceReponse.of(
                placeDto.id(),
                placeDto.placeType(),
                placeDto.placeName(),
                placeDto.address(),
                placeDto.phoneNumber(),
                placeDto.capacity(),
                placeDto.memo()
        );
    }
}
