package com.hyunseo.covidreserve.service;

import com.hyunseo.covidreserve.constant.ErrorCode;
import com.hyunseo.covidreserve.constant.EventStatus;
import com.hyunseo.covidreserve.constant.PlaceType;
import com.hyunseo.covidreserve.domain.Event;
import com.hyunseo.covidreserve.domain.Place;
import com.hyunseo.covidreserve.dto.EventDto;
import com.hyunseo.covidreserve.exception.GeneralException;
import com.hyunseo.covidreserve.repository.EventRepository;
import com.hyunseo.covidreserve.repository.PlaceRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;


/**
 * @author ihyeonseo
 */

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

        @InjectMocks private EventService sut;
        @Mock private EventRepository eventRepository;
        @Mock private PlaceRepository placeRepository;

        @DisplayName("이벤트를 검색하면, 결과를 출력하여 보여준다.")
        @Test
        void givenNothing_whenSearchEvents_thenReturnEntireEventList() {
            // given
            given(eventRepository.findAll(any(Predicate.class)))
                    .willReturn(List.of(
                            createEvent("오전 운동", true),
                            createEvent("오호 운동", false)
                    ));

            // when
            List<EventDto> list = sut.getEvents(new BooleanBuilder());

            // then
            assertThat(list).hasSize(2);
            verify(eventRepository).findAll(any(Predicate.class));
        }

        @DisplayName("이벤트를 검색하는데 에러가 발생하는 경우, 예약 기본 에러로 전환하여 예외를 던진다.")
        @Test
        void givenDataRelatedException_whenSearchEvents_thenThrowsGeneralException() {
            // given
            RuntimeException e = new RuntimeException("This is test.");
            given(eventRepository.findAll(any(Predicate.class)))
                    .willThrow(e);

            // when
            Throwable thrown = catchThrowable(() -> sut.getEvents(new BooleanBuilder()));

            // then
            assertThat(thrown)
                    .isInstanceOf(GeneralException.class )
                    .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());

            verify(eventRepository).findAll(any(Predicate.class));
        }

        @DisplayName("이벤트 ID로 존재하는 이벤트를 검색하면, 해당 이벤트 정보를 출력하여 보여준다.")
        @Test
        void givenEventId_whenSearchExistingEvent_thenReturnsEvent() {
            // given
            Event event = createEvent("오전 운동", true);
            Long eventId = 1L;

            given(eventRepository.findById(eventId)).willReturn(Optional.of(event));

            // when
            Optional<EventDto> result = sut.getEvent(eventId);

            // then
            assertThat(result).hasValue(EventDto.of(event));
            verify(eventRepository).findById(eventId);
        }


        @DisplayName("이벤트 ID로 존재하는 이벤트를 검색하면, 빈 정보를 출력하여 보여준다.")
        @Test
        void givenEventId_whenSearchNoneExistingEvent_thenReturnsEmptyOptional() {
            // given
            Long eventId = 2L;
            given(eventRepository.findById(eventId)).willReturn(Optional.empty());

            // when
            Optional<EventDto> result = sut.getEvent(eventId);

            // then
            assertThat(result).isEmpty();
            then(eventRepository).should().findById(eventId);
        }

        @DisplayName("이벤트 ID로 존재하는 이벤트를 검색하는데, 데이터 관련 오류가 난 경우, GeneralException으로 말아서 예외를 던진다.")
        @Test
        void givenDataRelatedException_whenSearchingEvent_thenThrowsGeneralException() {
            // given
            RuntimeException e = new RuntimeException("This is test.");
            given(eventRepository.findById(any())).willThrow(e);

            // when
            Throwable thrown = catchThrowable(() -> sut.getEvent(null));

            // then
            assertThat(thrown)
                    .isInstanceOf(GeneralException.class)
                    .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());

            then(eventRepository).should().findById(any());
        }

        @DisplayName("이벤트 정보를 주면, 이벤트를 생성하고 결과를 true 로 보여준다..")
        @Test
        void givenEvent_whenCreating_thenCreatesEventAndReturnsTrue() {
            // given
            EventDto eventDto = EventDto.of(createEvent("오후 운동", false));
            given(placeRepository.findById(eventDto.placeDto().id())).willReturn(Optional.of(createPlace()));
            given(eventRepository.save(any(Event.class))).willReturn(any());

            // when
            boolean result = sut.createEvent(eventDto);

            // then
            assertThat(result).isTrue();
            then(placeRepository).should().findById(eventDto.placeDto().id());
            then(eventRepository).should().save(any(Event.class));
        }

        @DisplayName("이벤트 정보를 주지 않으면, 생성을 중단하고 결과를 false 로 보여준다.")
        @Test
        void givenNothing_whenCreating_thenAbortCreatingAndReturnsFalse() {
            // given

            // when
            boolean result = sut.createEvent(null);

            // then
            assertThat(result).isFalse();
            then(placeRepository).shouldHaveNoInteractions();
            then(eventRepository).shouldHaveNoInteractions();

        }

        @DisplayName("이벤트 생성 중 장소 정보가 틀리거나 없으면, GeneralException으로 말아서 보낸다.")
        @Test
        void givenWrongPlaceId_whenCreating_thenThrowsGeneralException() {
            // GIven
            Event event = createEvent(null, false);
            given(placeRepository.findById(event.getPlace().getId())).willReturn(Optional.empty());

            // When
            Throwable thrown = catchThrowable(() -> sut.createEvent(EventDto.of(event)));

            // Then
            assertThat(thrown).isInstanceOf(GeneralException.class).hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());

            then(placeRepository).should().findById(event.getPlace().getId());
            then(eventRepository).shouldHaveNoInteractions();

        }

        @DisplayName("이벤트 생성 중 데이터 예외가 발생하면, GeneralException으로 말아서 보낸다.")
        @Test
        void givenDataRelatedException_whenCreating_thenThrowsGeneralException() {
            // given
            Event event = createEvent(null, false);
            RuntimeException e = new RuntimeException("This is test.");


            given(placeRepository.findById(event.getPlace().getId())).willReturn(Optional.of(createPlace()));
            given(eventRepository.save(any())).willThrow(e);

            // when
            Throwable thrown = catchThrowable(() -> sut.createEvent(EventDto.of(event)));

            // then
            assertThat(thrown)
                    .isInstanceOf(GeneralException.class)
                    .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
            then(placeRepository).should().findById(event.getPlace().getId());
            then(eventRepository).should().save(any());
        }

        @DisplayName("이벤트 ID와 정보를 주면, 이벤트 정보를 변경하고 결과를 true 로 보여준다.")
        @Test
        void givenEventIdAndItsInfo_whenModifying_thenModifiesEventAndReturnsTrue() {
            // given
            Long eventId = 1L;
            Event originalEvent = createEvent("오후 운동", false);
            Event changedEvent = createEvent("오전 운동", true);
            given(eventRepository.findById(eventId)).willReturn(Optional.of(originalEvent));
            given(eventRepository.save(changedEvent)).willReturn(changedEvent);

            // when
            boolean result = sut.modifyEvent(eventId, EventDto.of(changedEvent));

            // then
            assertThat(result).isTrue();
            assertThat(originalEvent.getEventName()).isEqualTo(changedEvent.getEventName());
            assertThat(originalEvent.getEventStartDatetime()).isEqualTo(changedEvent.getEventStartDatetime());
            assertThat(originalEvent.getEventEndDatetime()).isEqualTo(changedEvent.getEventEndDatetime());
            assertThat(originalEvent.getEventStatus()).isEqualTo(changedEvent.getEventStatus());
            verify(eventRepository).findById(eventId);
            verify(eventRepository).save(changedEvent);
        }

        @DisplayName("이벤트 ID를 주지 않으면, 이벤트 정보 변경을 중단하고 결과를 false 로 보여준다.")
        @Test
        void givenNoEventId_whenModifying_thenAbortModifyingAndReturnsFalse() {
            // given
            Event event = createEvent("오후 운동", false);

            // when
            boolean result = sut.modifyEvent(null, EventDto.of(event));

            // then
            assertThat(result).isFalse();
            then(eventRepository).shouldHaveNoInteractions();
        }

        @DisplayName("이벤트 ID만 주고 변경할 정보를 주지 않으면, 이벤트 정보 변경을 중단하고 결과를 false 로 보여준다.")
        @Test
        void givenEventIdOnly_whenModifying_thenAbortModifyingAndReturnsFalse() {
            // given
            Long eventId = 1L;
            Event originalEvent = createEvent("오후 운동", false);
            Event wrongEvent = createEvent("오전 운동", true);
            RuntimeException e = new RuntimeException("This is test.");

            given(eventRepository.findById(eventId)).willReturn(Optional.of(originalEvent));
            given(eventRepository.save(wrongEvent)).willThrow(e);

            // when
            Throwable thrown = catchThrowable(() -> sut.modifyEvent(eventId, EventDto.of(wrongEvent)));

            // then
            assertThat(thrown)
                    .isInstanceOf(GeneralException.class)
                    .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());
            then(eventRepository).should().findById(eventId);
            then(eventRepository).should().save(wrongEvent);
        }

        @DisplayName("이벤트 변경중 데이터 오류가 발생하면, GeneralException으로 말아서 보낸다.")
        @Test
        void givenDataRelatedException_whenModifying_thenThrowsGeneralException() {
            // given
            Long eventId = 1L;

            // when
            boolean result = sut.modifyEvent(eventId, null);

            // then
            assertThat(result).isFalse();
            then(eventRepository).shouldHaveNoInteractions();
        }

        @DisplayName("이벤트 ID를 주면, 이벤트 정보를 삭제하고 결과를 true 로 보여준다.")
        @Test
        void givenEventId_whenDeleting_thenDeletesEventAndReturnsTrue() {
            // given
            Long eventId = 1L;
            willDoNothing().given(eventRepository).deleteById(eventId);

            // when
            boolean result = sut.removeEvent(eventId);

            // then
            assertThat(result).isTrue();
            verify(eventRepository).deleteById(eventId);
        }

        @DisplayName("이벤트 ID를 주지 않으면, 이벤트 정보를 삭제를 중단하고 결과를 false 로 보여준다.")
        @Test
        void givenNothing_whenDeleting_thenAbortsDeletingAndReturnsFalse() {
            // given

            // when
            boolean result = sut.removeEvent(null);

            // then
            assertThat(result).isFalse();
            then(eventRepository).shouldHaveNoInteractions();
        }

        @DisplayName("이벤트 삭제 중 데이터 관련 오류가 발생하면, GeneralException으로 말아서 보낸다.")
        @Test
        void givenDataRelatedException_whenDeleting_thenThrowsGeneralException() {
            // given
            long eventId = 0L;
            RuntimeException e = new RuntimeException("This is test.");
            willThrow(e).given(eventRepository).deleteById(eventId);

            // when
            Throwable thrown = catchThrowable(() -> sut.removeEvent(eventId));

            // then
            assertThat(thrown)
                    .isInstanceOf(GeneralException.class)
                    .hasMessageContaining(ErrorCode.DATA_ACCESS_ERROR.getMessage());

            then(eventRepository).should().deleteById(eventId);
        }

        private Event createEvent(String eventName, boolean isMorning) {
            String hourStart = isMorning ? "09": "13";
            String hourEnd = isMorning ? "12": "16";

            return createEvent(
                    1L,
                    1L,
                    eventName,
                    EventStatus.OPENED,
                    LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourStart)),
                    LocalDateTime.parse("2021-01-01T%s:00:00".formatted(hourEnd))
            );
        }

        private Event createEvent(
                Long id,
                Long placeId,
                String eventName,
                EventStatus eventStatus,
                LocalDateTime eventStartDatetime,
                LocalDateTime eventEndDatetime
        ) {
            Event event = Event.of(
                    createPlace(placeId),
                    eventName,
                    eventStatus,
                    eventStartDatetime,
                    eventEndDatetime,
                    0,
                    24,
                    "마스크를 꼭 착용하세요"
                );
            ReflectionTestUtils.setField(event, "id", id);
            return event;
        }

        private Place createPlace() {
            return createPlace(1L);
        }

        private Place createPlace(long id) {
            Place place = Place.of(PlaceType.COMMON, "teset place", "test address", "010-1234-1234", 10, null);
            ReflectionTestUtils.setField(place, "id", id);
            return place;
        }

}