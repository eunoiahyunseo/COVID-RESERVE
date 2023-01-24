package com.hyunseo.covidreserve.service;

import com.hyunseo.covidreserve.constant.ErrorCode;
import com.hyunseo.covidreserve.constant.EventStatus;
import com.hyunseo.covidreserve.domain.Place;
import com.hyunseo.covidreserve.dto.EventDto;
import com.hyunseo.covidreserve.exception.GeneralException;
import com.hyunseo.covidreserve.repository.EventRepository;
import com.hyunseo.covidreserve.repository.PlaceRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * @author ihyeonseo
 */

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class EventService {

    private final EventRepository eventRepository;
    private final PlaceRepository placeRepository;

    @Transactional(readOnly = true)
    public List<EventDto> getEvents(Predicate predicate) {
        try {
            return StreamSupport.stream(eventRepository.findAll(predicate).spliterator(), false)
                    .map(EventDto::of)
                    .toList();
        } catch(Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public List<EventDto> getEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    ) {
        try {
            return null;
        } catch(Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional(readOnly = true)
    public Optional<EventDto> getEvent(Long eventId) {
        try {
            return eventRepository.findById(eventId).map(EventDto::of);
        } catch(Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);

        }
    }

    @Transactional()
    public boolean createEvent(EventDto eventDTO) {
        try {
            if(eventDTO == null) {
                return false;
            }

            Place place = placeRepository.findById(eventDTO.placeDto().id()).orElseThrow(()
                    -> new GeneralException(ErrorCode.NOT_FOUND));

            eventRepository.save(eventDTO.toEntity(place));

            return true;
        } catch(Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }

    }

    @Transactional()
    public boolean modifyEvent(Long eventId, EventDto eventDTO) {
        try {
            if(eventId == null || eventDTO == null) {
                return false;
            }

            eventRepository.findById(eventId)
                    .ifPresent(event -> eventRepository.save(eventDTO.updateEntity(event)));

            return true;
        } catch(Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    @Transactional()
    public boolean removeEvent(Long eventId) {
        try {
            if(eventId == null) {
                return false;
            }
            eventRepository.deleteById(eventId);

            return true;
        } catch(Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

}
