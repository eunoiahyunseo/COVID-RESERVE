package com.hyunseo.covidreserve.controller.api;

import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ihyeonseo
 * @created 05/01/2023 - 6:49 PM
 */
@RequestMapping("/api")
@RestController
public class APIPlaceController {

    @GetMapping("/places")
    public List<String> getPlaces() {
        return List.of("place1", "place2");
    }

    @PostMapping("/places")
    public Boolean createPlace() {
        return true;
    }

    @GetMapping("/places/{placeId}")
    public String getPlace(@PathVariable Integer placeId) {
        return "place " + placeId;
    }

    @PutMapping("/places/{placeId}")
    public Boolean modifyPlace(@PathVariable Integer placeId) {
        return true;
    }

    @DeleteMapping("/places/{placeId}")
    public Boolean removePlace(@PathVariable Integer placeId) {
        return true;
    }
}

