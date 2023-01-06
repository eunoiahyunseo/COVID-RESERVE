package com.hyunseo.covidreserve.controller;

import com.hyunseo.covidreserve.constant.EventStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ihyeonseo
 */

@RequestMapping("/events")
@Controller
public class EventController {

    @GetMapping("/")
    public ModelAndView events(
            Integer placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("placeName", "place-" + placeId);
        map.put("eventName", eventName);
        map.put("eventStatus", eventStatus);
        map.put("eventStartDatetime", eventStartDatetime);
        map.put("eventEndDatetime", eventEndDatetime);

        return new ModelAndView("admin/events", map);
    }

    @GetMapping("/{eventId}")
    public String eventDetail(@PathVariable Integer eventId) {
        return "event/detail";
    }

}