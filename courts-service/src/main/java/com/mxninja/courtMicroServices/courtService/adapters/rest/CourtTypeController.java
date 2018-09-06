package com.mxninja.courtMicroServices.courtService.adapters.rest;

import com.mxninja.courtMicroServices.courtService.adapters.respositories.CourtTypeMongoRepository;
import com.mxninja.courtMicroServices.courtService.adapters.respositories.CourtTypeProjection;
import com.mxninja.courtMicroServices.courtService.domain.CourtTypeAggregation;
import com.mxninja.courtMicroServices.models.ResponseModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 9/4/2018
 *
 * @author Mohammad Ali
 */

@RestController
@RequestMapping("courts/types")
public class CourtTypeController {

    private final CourtTypeMongoRepository courtTypeRepository;

    public CourtTypeController(CourtTypeMongoRepository courtTypeRepository) {
        this.courtTypeRepository = courtTypeRepository;
    }

    @PostMapping
    public ResponseModel<CourtTypeProjection> save(@RequestBody CourtTypeAggregation aggregation) {
        return new ResponseModel<>(courtTypeRepository.save(aggregation));
    }

}
