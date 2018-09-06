package com.mxninja.courtMicroServices.courtService.adapters.rest;

import com.mxninja.courtMicroServices.courtService.adapters.respositories.CourtMongoRepository;
import com.mxninja.courtMicroServices.courtService.adapters.respositories.CourtProjection;
import com.mxninja.courtMicroServices.courtService.domain.CourtServiceAggregation;
import com.mxninja.courtMicroServices.courtService.exceptions.CourtNotFoundException;
import com.mxninja.courtMicroServices.models.ResponseModel;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 9/5/2018
 *
 * @author Mohammad Ali
 */

@RestController
@RequestMapping("services")
public class CourtServiceController {

    private final CourtMongoRepository courtMongoRepository;

    public CourtServiceController(CourtMongoRepository courtMongoRepository) {
        this.courtMongoRepository = courtMongoRepository;
    }

    @PostMapping("/courtId/{courtId}")
    public ResponseModel<Boolean> addServiceToCourt(
            @PathVariable("courtId") String courtId,
            @RequestBody CourtServiceAggregation body) {
        ObjectId objectId = new ObjectId(courtId);
        Optional<CourtProjection> optional = courtMongoRepository.findById(objectId);
        if (!optional.isPresent()) {
            throw new CourtNotFoundException();
        }
        return new ResponseModel<>(courtMongoRepository.addService(objectId, body));
    }

}
