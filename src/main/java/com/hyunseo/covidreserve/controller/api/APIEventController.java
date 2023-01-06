package com.hyunseo.covidreserve.controller.api;

import com.hyunseo.covidreserve.exception.GeneralException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ihyeonseo
 */
@RequestMapping("/api")
@RestController
public class APIEventController {

    @GetMapping("/events")
    public List<String> getEvents() throws Exception {
        throw new HttpRequestMethodNotSupportedException("GET");
        // return List.of("event1", "event2");
    }

    // 우리가 지정한 error가 아니므로, 공통 Error page로 넘어가게 됩니다.
    @PostMapping("/events")
    public Boolean createEvent() { 
        throw new GeneralException("장군님");
        // return true;
    }

    @GetMapping("/events/{eventId}")
    public String getEvent(@PathVariable Integer eventId) {
        throw new RuntimeException("런타임 에러");
        // return "event " + eventId;
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