package com.hyunseo.covidreserve.dto;

import com.hyunseo.covidreserve.constant.EventStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.NonNull;

import java.time.LocalDateTime;

/**
 * @author ihyeonseo
 */
public record EventRequest(
        @NonNull @Positive Long placeId,
        @NotBlank String eventName,
        @NonNull EventStatus eventStatus,
        @NonNull LocalDateTime eventStartDatetime,
        @NonNull LocalDateTime eventEndDatetime,
        @NonNull @PositiveOrZero Integer currentNumberOfPeople,
        @NonNull @Positive Integer capacity,
        String memo
) {

    public static EventRequest of(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime,
            Integer currentNumberOfPeople,
            Integer capacity,
            String memo
    ) {
        return new EventRequest(
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                currentNumberOfPeople,
                capacity,
                memo
        );
    }

    public EventDto toDTO() {
        return EventDto.of(
                null,
                null, // TODO: 여기를 반드시 적절히 고쳐야 사용할 수 있음
                this.eventName(),
                this.eventStatus(),
                this.eventStartDatetime(),
                this.eventEndDatetime(),
                this.currentNumberOfPeople(),
                this.capacity(),
                this.memo(),
                null,
                null
        );
    }
}
