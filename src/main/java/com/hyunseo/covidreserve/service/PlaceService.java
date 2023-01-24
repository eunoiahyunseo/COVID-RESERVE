package com.hyunseo.covidreserve.service;

import com.hyunseo.covidreserve.constant.ErrorCode;
import com.hyunseo.covidreserve.dto.PlaceDto;
import com.hyunseo.covidreserve.exception.GeneralException;
import com.hyunseo.covidreserve.repository.PlaceRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

/**
 * @author ihyeonseo
 */

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    public List<PlaceDto> getPlaces(Predicate predicate) {
        try {
            return StreamSupport.stream(placeRepository.findAll(predicate).spliterator(), false)
                    .map(PlaceDto::of)
                    .toList();
        } catch(Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public Optional<PlaceDto> getPlace(Long placeId) {
        try {
            return placeRepository.findById(placeId).map(PlaceDto::of);
        } catch(Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean createPlace(PlaceDto placeDto) {
        try {
            if(placeDto == null) {
                return false;
            }

            placeRepository.save(placeDto.toEntity());
            return true;
        } catch(Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean modifyPlace(Long placeId, PlaceDto dto) {
        try {
            if(placeId == null || dto == null) {
                return false;
            }
            placeRepository.findById(placeId)
                    .ifPresent(place -> placeRepository.save(dto.updateEntity(place)));
            return true;
        } catch(Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }

    public boolean removePlace(Long placeId) {
        try {
            if(placeId == null) {
                return false;
            }

            placeRepository.deleteById(placeId);
            return true;
        } catch(Exception e) {
            throw new GeneralException(ErrorCode.DATA_ACCESS_ERROR, e);
        }
    }


}
