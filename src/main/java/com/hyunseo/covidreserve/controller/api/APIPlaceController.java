package com.hyunseo.covidreserve.controller.api;

import com.hyunseo.covidreserve.constant.PlaceType;
import com.hyunseo.covidreserve.domain.Place;
import com.hyunseo.covidreserve.dto.APIDataResponse;
import com.hyunseo.covidreserve.dto.PlaceDTO;
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
    public APIDataResponse<List<PlaceDTO>> getPlaces() {
        return APIDataResponse.of(List.of(PlaceDTO.of(
                PlaceType.COMMON,
                "행복한필라테스",
                "배울2로 61 1013동 1203호",
                "010-2488-4113",
                45,
                "신장개업"
        )));
    }

    @PostMapping("/places")
    public Boolean createPlace() {
        return true;
    }

    @GetMapping("/places/{placeId}")
    public APIDataResponse<PlaceDTO> getPlace(@PathVariable Integer placeId) {
        if(placeId.equals(2)) {
            return APIDataResponse.of(null);
        }

        return APIDataResponse.of(PlaceDTO.of(
                PlaceType.COMMON,
                "행복한필라테스",
                "배울2로 61 1013동 1203호",
                "010-2488-4113",
                45,
                "신장개업"
        ));
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

