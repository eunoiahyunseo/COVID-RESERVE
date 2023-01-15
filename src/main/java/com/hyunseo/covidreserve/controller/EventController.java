package com.hyunseo.covidreserve.controller;

import com.hyunseo.covidreserve.aop.annotation.Timer;
import com.hyunseo.covidreserve.constant.EventStatus;
import com.hyunseo.covidreserve.dto.EventResponse;
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
public class EventController {

    // @Timer
    @GetMapping
    public ModelAndView events(
            Integer placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    ) {
        Map<String, Object> map = new HashMap<>();
        map.put("events", List.of(EventResponse.of(
                        1L,
                        "오후 운동",
                        EventStatus.OPENED,
                        LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                        LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                        0,
                        24,
                        "마스크 꼭 착용하세요"
                ), EventResponse.of(
                        2L,
                        "오후 운동",
                        EventStatus.OPENED,
                        LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                        LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                        0,
                        24,
                        "마스크 꼭 착용하세요"
                )
        ));

        return new ModelAndView("event/index", map);
    }

    @Timer
    @GetMapping("/{eventId}")
    public ModelAndView eventDetail(@PathVariable Long eventId) throws Exception {
        Map<String, Object> map = new HashMap<>();

        Thread.sleep(3000);

        // TODO: 임시 데이터. 추후 삭제 예정
        map.put("event", EventResponse.of(
                eventId,
                "오후 운동",
                EventStatus.OPENED,
                LocalDateTime.of(2021, 1, 1, 13, 0, 0),
                LocalDateTime.of(2021, 1, 1, 16, 0, 0),
                0,
                24,
                "마스크 꼭 착용하세요"
        ));

        return new ModelAndView("event/detail", map);
    }
}