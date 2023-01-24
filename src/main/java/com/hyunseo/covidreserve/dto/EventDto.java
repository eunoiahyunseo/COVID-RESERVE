package com.hyunseo.covidreserve.dto;

import com.hyunseo.covidreserve.constant.EventStatus;
import com.hyunseo.covidreserve.domain.Event;
import com.hyunseo.covidreserve.domain.Place;

import java.time.LocalDateTime;

/**
 * @author ihyeonseo
 */
public record EventDto(
        Long id,
        PlaceDto placeDto,
        String eventName,
        EventStatus eventStatus,
        LocalDateTime eventStartDatetime,
        LocalDateTime eventEndDatetime,
        Integer currentNumberOfPeople,
        Integer capacity,
        String memo,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt
) {

    // EventDTO
    public static EventDto of(
            Long id,
            PlaceDto placeDto,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt

    ) {
        return new EventDto(id, placeDto, eventName, eventStatus, eventStartDatetime, eventEndDatetime,
                currentNumberOfPeople, capacity, memo, createdAt, modifiedAt);
    }


    // Event entity -> EventDTO
    public static EventDto of(Event event) {
        return new EventDto(
                event.getId(),
                PlaceDto.of(event.getPlace()),
                event.getEventName(),
                event.getEventStatus(),
                event.getEventStartDatetime(),
                event.getEventEndDatetime(),
                event.getCurrentNumberOfPeople(),
                event.getCapacity(),
                event.getMemo(),
                event.getCreatedAt(),
                event.getModifiedAt()
        );
    }

    // EventDTO -> Event entity
    public Event toEntity(Place place) {
        return Event.of(
                place,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }

    // Update Event Entity
    public Event updateEntity(Event event) {
        if (eventName != null) { event.setEventName(eventName); }
        if (eventStatus != null) { event.setEventStatus(eventStatus); }
        if (eventStartDatetime != null) { event.setEventStartDatetime(eventStartDatetime); }
        if (eventEndDatetime != null) { event.setEventEndDatetime(eventEndDatetime); }
        if (currentNumberOfPeople != null) { event.setCurrentNumberOfPeople(currentNumberOfPeople); }
        if (capacity != null) { event.setCapacity(capacity); }
        if (memo != null) { event.setMemo(memo); }

        return event;
    }
}
