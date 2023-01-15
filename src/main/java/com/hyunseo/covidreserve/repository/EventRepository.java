package com.hyunseo.covidreserve.repository;

import com.hyunseo.covidreserve.constant.EventStatus;
import com.hyunseo.covidreserve.domain.Event;
import com.hyunseo.covidreserve.dto.EventDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author ihyeonseo
 */
public interface EventRepository extends JpaRepository<Event, Long> {
    default List<EventDTO> findEvents(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    ) { return List.of(); }

    default Optional<EventDTO> findEvent(Long eventId) { return Optional.empty(); }
    default boolean insertEvent(EventDTO eventDTO) { return false; }
    default boolean updateEvent(Long eventId, EventDTO dto) { return false; }
    default boolean deleteEvent(Long eventId) { return false; }
}
