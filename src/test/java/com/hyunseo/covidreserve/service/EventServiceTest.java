package com.hyunseo.covidreserve.service;

import com.hyunseo.covidreserve.constant.EventStatus;
import com.hyunseo.covidreserve.dto.EventDTO;
import com.hyunseo.covidreserve.repository.EventRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.BDDMockito.verify;



/**
 * @author ihyeonseo
 */

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @InjectMocks private EventService sut; // service에다가 eventrepository를 mock해야 한다.
    @Mock private EventRepository eventRepository;


    @DisplayName("검색조건 없이 이벤트를 검색하면, 전체 결과를 출력하여 보여준다.")
    @Test
    void givenNothing_whenSearchEvents_thenReturnEntireEventList() {
        // given
        given(eventRepository.findEvents(null, null, null, null, null))
                .willReturn(List.of(
                        createEventDTO(1L, "오전 운동", true),
                        createEventDTO(1L, "오후 운동", false)
                ));

        // when
        List<EventDTO> list = sut.getEvents(null, null, null, null, null);

        // then
        assertThat(list).hasSize(2);
        // then(eventRepository).should(times(1)).findEvents(null, null, null, null, null);
        verify(eventRepository).findEvents(null, null, null, null, null);
    }

    @DisplayName("검색조건과 함께 이벤트를 검색하면, 검색된 결과를 출력하여 보여준다.")
    @Test
    void givenSearchParams_whenSearchEvents_thenReturnEventList() {
        // given
        Long placeId = 1L;
        String eventName = "오후 운동";
        EventStatus eventStatus = EventStatus.OPENED;
        LocalDateTime eventStartDatetime = LocalDateTime.of(2021, 1, 1, 0, 0, 0);
        LocalDateTime eventEndDatetime = LocalDateTime.of(2021, 1, 2, 0, 0, 0);


        given(eventRepository.findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime))
                .willReturn(List.of(
                        createEventDTO(1L, "오후 운동", eventStatus, eventStartDatetime, eventEndDatetime)
                ));
        // when
        List<EventDTO> list = sut.getEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);

        // then
        assertThat(list)
                .hasSize(1)
                .allSatisfy(event -> {
                    assertThat(event)
                            .hasFieldOrPropertyWithValue("placeId", placeId)
                            .hasFieldOrPropertyWithValue("eventName", eventName)
                            .hasFieldOrPropertyWithValue("eventStatus", eventStatus);
                    assertThat(event.eventStartDatetime()).isAfterOrEqualTo(eventStartDatetime);
                    assertThat(event.eventEndDatetime()).isBeforeOrEqualTo(eventEndDatetime);
                });
         verify(eventRepository).findEvents(placeId, eventName, eventStatus, eventStartDatetime, eventEndDatetime);

    }

    @DisplayName("이벤트 ID로 존재하는 이벤트를 검색하면, 해당 이벤트 정보를 출력하여 보여준다.")
    @Test
    void givenEventId_whenSearchExistingEvent_thenReturnsEvent() {
        // given
        Long eventId = 1L;
        EventDTO eventDTO = createEventDTO(1L, "오전 운동", true);

        given(eventRepository.findEvent(eventId)).willReturn(Optional.of(eventDTO));

        // when
        Optional<EventDTO> result = sut.getEvent(eventId);

        // then
        assertThat(result).hasValue(eventDTO);
        verify(eventRepository).findEvent(eventId);
    }


    @DisplayName("이벤트 ID로 존재하는 이벤트를 검색하면, 빈 정보를 출력하여 보여준다.")
    @Test
    void givenEventId_whenSearchNoneExistingEvent_thenReturnsEmptyOptional() {
        // given
        Long eventId = 2L;
        given(eventRepository.findEvent(eventId)).willReturn(Optional.empty());

        EventDTO eventDTO = createEventDTO(1L, "오전 운동", true);
        // when
        Optional<EventDTO> result = sut.getEvent(eventId);

        // then
        assertThat(result).isEmpty();
        then(eventRepository).should().findEvent(eventId);

    }

    @DisplayName("이벤트 정보를 주면, 이벤트를 생성하고 결과를 true 로 보여준다..")
    @Test
    void givenEvent_whenCreating_thenCreatesEventAndReturnsTrue() {
        // given
        EventDTO dto = createEventDTO(2L, "오후 운동", false);
        given(eventRepository.insertEvent(dto)).willReturn(true);

        // when
        boolean result = sut.createEvent(dto);

        // then
        assertThat(result).isTrue();
        then(eventRepository).should().insertEvent(dto);
    }

    @DisplayName("이벤트 정보를 주지 않으면, 생성을 중단하고 결과를 false 로 보여준다.")
    @Test
    void givenNothing_whenCreating_thenAbortCreatingAndReturnsFalse() {
        // given
        given(eventRepository.insertEvent(null)).willReturn(false);

        // when
        boolean result = sut.createEvent(null);

        // then
        assertThat(result).isFalse();
        then(eventRepository).should().insertEvent(null);

    }

    @DisplayName("이벤트 ID와 정보를 주면, 이벤트 정보를 변경하고 결과를 true 로 보여준다.")
    @Test
    void givenEventIdAndItsInfo_whenModifying_thenModifiesEventAndReturnsTrue() {
        // given
        Long eventId = 1L;
        EventDTO dto = createEventDTO(2L, "오후 운동", false);
        given(eventRepository.updateEvent(eventId, dto)).willReturn(true);

        // when
        boolean result = sut.modifyEvent(eventId, dto);

        // then
        assertThat(result).isTrue();
        verify(eventRepository).updateEvent(eventId, dto);

    }

    @DisplayName("이벤트 ID를 주지 않으면, 이벤트 정보 변경을 중단하고 결과를 false 로 보여준다.")
    @Test
    void givenNoEventId_whenModifying_thenAbortModifyingAndReturnsFalse() {
        // given
        EventDTO dto = createEventDTO(2L, "오후 운동", false);
        given(eventRepository.updateEvent(null, dto)).willReturn(false);

        // when
        boolean result = sut.modifyEvent(null, dto);

        // then
        assertThat(result).isFalse();
        verify(eventRepository).updateEvent(null, dto);

    }


    @DisplayName("이벤트 ID만 주고 변경할 정보를 주지 않으면, 이벤트 정보 변경을 중단하고 결과를 false 로 보여준다.")
    @Test
    void givenEventIdOnly_whenModifying_thenAbortModifyingAndReturnsFalse() {
        // given
        Long eventId = 1L;
        given(eventRepository.updateEvent(eventId, null)).willReturn(false);

        // when
        boolean result = sut.modifyEvent(eventId, null);

        // then
        assertThat(result).isFalse();
        verify(eventRepository).updateEvent(eventId, null);
    }

    @DisplayName("이벤트 ID를 주면, 이벤트 정보를 삭제하고 결과를 true 로 보여준다.")
    @Test
    void givenEventId_whenDeleting_thenDeletesEventAndReturnsTrue() {
        // given
        Long eventId = 1L;
        given(eventRepository.deleteEvent(eventId)).willReturn(true);

        // when
        boolean result = sut.removeEvent(eventId);

        // then
        assertThat(result).isTrue();
        verify(eventRepository).deleteEvent(eventId);
    }

    @DisplayName("이벤트 ID를 주지 않으면, 이벤트 정보를 삭제를 중단하고 결과를 false 로 보여준다.")
    @Test
    void givenNothing_whenDeleting_thenAbortsDeletingAndReturnsFalse() {
        // given
        given(eventRepository.deleteEvent(null)).willReturn(false);

        // when
        boolean result = sut.removeEvent(null);

        // then
        assertThat(result).isFalse();
        verify(eventRepository).deleteEvent(null);
    }

    private EventDTO createEventDTO(Long placeId, String eventName, boolean isMorning) {
        String hourStart = isMorning ? "09": "13";
        String hourEnd = isMorning ? "12": "16";

        return createEventDTO(
                placeId,
                eventName,
                EventStatus.OPENED,
                LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourStart)),
                LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourEnd))
        );
    }

    private EventDTO createEventDTO(
            Long placeId,
            String eventName,
            EventStatus eventStatus,
            LocalDateTime eventStartDatetime,
            LocalDateTime eventEndDatetime
    ) {
        return EventDTO.of(
                placeId,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                0,
                24,
                "마스크를 꼭 착용하세요",
                LocalDateTime.now(),
                LocalDateTime.now()
            );
    }

}