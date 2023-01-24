package com.hyunseo.covidreserve.controller.api;

import com.hyunseo.covidreserve.constant.PlaceType;
import com.hyunseo.covidreserve.dto.APIDataResponse;
import com.hyunseo.covidreserve.dto.PlaceDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ihyeonseo
 */

@Deprecated
//@RequestMapping("/api")
//@RestController
public class ApiPlaceController {

    @GetMapping("/places")
    public APIDataResponse<List<PlaceDto>> getPlaces() {
        return APIDataResponse.of(List.of(PlaceDto.of(
                1L,
                PlaceType.COMMON,
                "행복한필라테스",
                "배울2로 61 1013동 1203호",
                "010-2488-4113",
                45,
                "신장개업",
                null,
                null
        )));
    }

    @PostMapping("/places")
    public Boolean createPlace() {
        return true;
    }

    @GetMapping("/places/{placeId}")
    public APIDataResponse<PlaceDto> getPlace(@PathVariable Integer placeId) {
        if(placeId.equals(2)) {
            return APIDataResponse.of(null);
        }

        return APIDataResponse.of(PlaceDto.of(
                1L,
                PlaceType.COMMON,
                "행복한필라테스",
                "배울2로 61 1013동 1203호",
                "010-2488-4113",
                45,
                "신장개업",
                null,
                null
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

