package com.hyunseo.covidreserve.controller;

import com.hyunseo.covidreserve.aop.annotation.Timer;
import com.hyunseo.covidreserve.constant.ErrorCode;
import com.hyunseo.covidreserve.constant.EventStatus;
import com.hyunseo.covidreserve.domain.Event;
import com.hyunseo.covidreserve.dto.EventResponse;
import com.hyunseo.covidreserve.exception.GeneralException;
import com.hyunseo.covidreserve.service.EventService;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ihyeonseo
 */


@RequestMapping("/events")
@Controller
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    // @Timer
    @GetMapping
    public ModelAndView events(
            @QuerydslPredicate(root = Event.class) Predicate predicate
            ) {
        Map<String, Object> map = new HashMap<>();
        List<EventResponse> events = eventService.getEvents(predicate)
                .stream()
                .map(EventResponse::from)
                .toList();

        map.put("events", events);

        return new ModelAndView("event/index", map);
    }

    @Timer
    @GetMapping("/{eventId}")
    public ModelAndView eventDetail(@PathVariable Long eventId) {
        Map<String, Object> map = new HashMap<>();
        EventResponse event = eventService.getEvent(eventId)
                .map(EventResponse::from)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));

        map.put("event", event);

        return new ModelAndView("event/detail", map);
    }
}