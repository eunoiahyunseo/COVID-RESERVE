package com.hyunseo.covidreserve.controller;

import com.hyunseo.covidreserve.constant.ErrorCode;
import com.hyunseo.covidreserve.domain.Place;
import com.hyunseo.covidreserve.dto.PlaceReponse;
import com.hyunseo.covidreserve.exception.GeneralException;
import com.hyunseo.covidreserve.service.PlaceService;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ihyeonseo
 */

@RequiredArgsConstructor
@RequestMapping("/places")
@Controller
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ModelAndView places(@QuerydslPredicate(root = Place.class) Predicate predicate) {
        Map<String, Object> map = new HashMap<>();
        List<PlaceReponse> places = placeService.getPlaces(predicate)
                .stream()
                .map(PlaceReponse::from)
                .toList();
        map.put("places", places);

        return new ModelAndView("place/index", map);
    }

    @GetMapping("/{placeId}")
    public ModelAndView placeDetail(@PathVariable Long placeId) {
        Map<String, Object> map = new HashMap<>();
        PlaceReponse place = placeService.getPlace(placeId)
                .map(PlaceReponse::from)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));

        map.put("place", place);

        return new ModelAndView("place/detail", map);
    }

}