package com.hyunseo.covidreserve.service;

import com.hyunseo.covidreserve.constant.EventStatus;
import com.hyunseo.covidreserve.dto.EventDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ihyeonseo
 */

@Service
public class EventServiceImpl implements EventService {
    @Override
    public List<EventDTO> findEvents(Long placeId, String eventName, EventStatus eventStatus,
                                     LocalDateTime eventStartDatetime, LocalDateTime eventEndDatetime) {
        return null;
    }
}
