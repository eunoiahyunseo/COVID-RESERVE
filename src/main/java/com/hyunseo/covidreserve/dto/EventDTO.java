package com.hyunseo.covidreserve.dto;

import com.hyunseo.covidreserve.constant.EventStatus;

import java.time.LocalDateTime;

/**
 * @author ihyeonseo
 */
public record EventDTO(
        Long placeId,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDatetime,
        LocalDateTime eventEndDatetime,
        Integer currentNumberOfPeople,
        Integer capacity,
        String memo
) {
    public static EventDTO of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventDTO(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime,
                currentNumberOfPeople, capacity, memo);
    }
}
