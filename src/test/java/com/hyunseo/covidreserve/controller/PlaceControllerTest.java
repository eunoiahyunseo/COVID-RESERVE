package com.hyunseo.covidreserve.controller;

import com.hyunseo.covidreserve.dto.PlaceDto;
import com.hyunseo.covidreserve.service.PlaceService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author ihyeonseo
 */

@DisplayName("View 컨트롤러 - 장소")
@WebMvcTest(PlaceController.class)
class PlaceControllerTest {

    private final MockMvc mvc;

    @MockBean private PlaceService placeService;

    public PlaceControllerTest(@Autowired MockMvc mvc) {
        this.mvc = mvc;
    }

    @DisplayName("[view][GET] 장소 리스트 페이지")
    @Test
    void givenNothing_whenRequestingPlacePage_thenReturnsPlacesPage() throws Exception {
        // GIven
        given(placeService.getPlaces(any())).willReturn(List.of());

        // When & Then
        mvc.perform(get("/places"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("place/index"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("places"));

        verify(placeService).getPlaces(any());
    }

    @DisplayName("[view][GET] 장소 세부 정보 페이지")
    @Test
    void givenPlaceId_whenRequestingPlaceDetailPage_thenReturnsPlaceDetailPage() throws Exception {
        // GIven
        long placeId = 1L;
        given(placeService.getPlace(placeId)).willReturn(Optional.of(
                PlaceDto.of(null, null, null, null, null, null, null, null, null)
        ));

        // When & Then
        mvc.perform(get("/places/" + placeId))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_HTML))
                .andExpect(view().name("place/detail"))
                .andExpect(model().hasNoErrors())
                .andExpect(model().attributeExists("place"));

        verify(placeService).getPlace(placeId);
    }
}