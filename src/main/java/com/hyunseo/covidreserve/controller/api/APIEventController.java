package com.hyunseo.covidreserve.controller.api;

import com.hyunseo.covidreserve.constant.EventStatus;
import com.hyunseo.covidreserve.dto.APIDataResponse;
import com.hyunseo.covidreserve.dto.EventRequest;
import com.hyunseo.covidreserve.dto.EventResponse;
import com.hyunseo.covidreserve.service.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ihyeonseo
 */

@Slf4j
//@Validated
@RequiredArgsConstructor
//@RequestMapping("/api")
//@RestController
public class APIEventController {

    private final EventService eventService;

    // [장소, 이름, 상태, 기간]로 필터잉을 가능하게 할 것이다.
    @GetMapping("/events")
    public APIDataResponse<List<EventResponse>> getEvents(
            @Positive Long placeId,
            @Size(min = 2) String eventName,
            EventStatus eventStatus,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDatetime,
             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDatetime
    ) throws Exception {
        List<EventResponse> response =
                eventService.getEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime)
                        .stream().map(EventResponse::from).toList();

        return APIDataResponse.of(response);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/events")
    public APIDataResponse<String> createEvent(@Valid @RequestBody EventRequest eventRequest) {
        log.debug("보고 싶은 값은 : {}", eventRequest);
        boolean result = eventService.createEvent(eventRequest.toDTO());
        return APIDataResponse.of(Boolean.toString(result));
    }

    @GetMapping("/events/{eventId}")
    public APIDataResponse<EventResponse> getEvent(@PathVariable Long eventId) {
        EventResponse eventResponse = EventResponse.from(
                eventService.getEvent(eventId).orElse(null));
        return APIDataResponse.of(eventResponse);
    }

    @PutMapping("/events/{eventId}")
    public APIDataResponse<String> modifyEvent(
            @PathVariable Long eventId,
            @RequestBody EventRequest eventRequest
    ) {
        boolean result = eventService.modifyEvent(eventId, eventRequest.toDTO());
        return APIDataResponse.of(Boolean.toString(result));
    }

    @DeleteMapping("/events/{eventId}")
    public APIDataResponse<String> removeEvent(@PathVariable Long eventId) {
        boolean result = eventService.removeEvent(eventId);
        return APIDataResponse.of(Boolean.toString(result));
    }
}