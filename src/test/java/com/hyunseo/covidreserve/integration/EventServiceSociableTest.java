package com.hyunseo.covidreserve.integration;

import com.hyunseo.covidreserve.dto.EventDTO;
import com.hyunseo.covidreserve.service.EventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

/**
 * @author ihyeonseo
 */

@SpringBootTest
public class EventServiceSociableTest {

    @Autowired private EventService sut;

    @DisplayName("검색조건 없이 이벤트를 검색하면, 전체 결과를 출력하여 보여준다.")
    @Test
    void givenNothing_whenSearchEvents_thenReturnEntireEventList() {
        // given

        // when
        List<EventDTO> list = sut.getEvents(3L, null, null, null, null);

        // then
        assertThat(list).hasSize(0);
    }



}
