package com.hyunseo.covidreserve.controller.api;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ihyeonseo
 */
@RequestMapping("/api")
@RestController
public class APIEventController {

    @GetMapping("/events")
    public List<String> getEvents() {
        //throw new GeneralException("테스트 에러");
        return List.of("event1", "event2");
    }

    // 우리가 지정한 error가 아니므로, 공통 Error page로 넘어가게 됩니다.
    @PostMapping("/events")
    public Boolean createEvent() {
        // throw new RuntimeException("run-time 테스트 에러");
        return true;
    }

    @GetMapping("/events/{eventId}")
    public String getEvent(@PathVariable Integer eventId) {
        return "event " + eventId;
    }

    @PutMapping("/events/{eventId}")
    public Boolean modifyEvent(@PathVariable Integer eventId) {
        return true;
    }

    @DeleteMapping("/events/{eventId}")
    public Boolean removeEvent(@PathVariable Integer eventId) {
        return true;
    }
}