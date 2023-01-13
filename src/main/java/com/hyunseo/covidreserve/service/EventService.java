package com.hyunseo.covidreserve.service;

import com.hyunseo.covidreserve.constant.ErrorCode;
import com.hyunseo.covidreserve.constant.EventStatus;
import com.hyunseo.covidreserve.dto.EventDTO;
import com.hyunseo.covidreserve.exception.GeneralException;
import com.hyunseo.covidreserve.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author ihyeonseo
 */

@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    public List<EventDTO> getEvents(Long placeId,
                                    String eventName,
                                    EventStatus eventStatus,
                                    LocalDateTime eventStartDatetime,
                                    LocalDateTime eventEndDatetime
    ) {
        try {
            return eventRepository.findEvents(
                    placeId,
                    eventName,
                    eventStatus,
                    eventStartDatetime,
                    eventEndDatetime
            );
        } catch(Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public Optional<EventDTO> getEvent(Long eventId) {
        return eventRepository.findEvent(eventId);
    }

    public boolean createEvent(EventDTO eventDTO) {
        return eventRepository.insertEvent(eventDTO);
    }

    public boolean modifyEvent(Long eventId, EventDTO eventDTO) {
        return eventRepository.updateEvent(eventId, eventDTO);
    }

    public boolean removeEvent(Long eventId) {
        return eventRepository.deleteEvent(eventId);
    }






}
