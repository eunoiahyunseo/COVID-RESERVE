package com.hyunseo.covidreserve.service;

import com.hyunseo.covidreserve.constant.EventStatus;
import com.hyunseo.covidreserve.dto.EventDTO;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 이벤트 서비스
 *
 * @author ihyeonseo
 */
public interface EventService  {
    /**
     * 검색어를 받아서 이벤트 리스트 반환
     *
     * @param placeId 장소 ID
     * @param eventName 이벤트 이름
     * @param eventStatus 이벤트 상태
     * @param eventStartDatetime 시작 시간
     * @param eventEndDatetime 종료 시간
     * @return
     */
    List<EventDTO> findEvents(Long placeId,
                              String eventName,
                              EventStatus eventStatus,
                              LocalDateTime eventStartDatetime,
                              LocalDateTime eventEndDatetime);
}
