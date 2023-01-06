package com.hyunseo.covidreserve.domain;
import com.hyunseo.covidreserve.constant.EventStatus;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author ihyeonseo
 * @created 05/01/2023 - 9:17 PM
 */

@Data
public class Event {
    private Long id;

    private Long placeId;
    private String eventName;
    private EventStatus eventStatus;
    private LocalDateTime eventStartDatetime;
    private LocalDateTime eventEndDatetime;
    private Integer currentNumberOfPeople;
    private Integer capacity;
    private String memo;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}