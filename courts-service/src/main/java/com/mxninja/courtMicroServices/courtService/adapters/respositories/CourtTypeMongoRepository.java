package com.mxninja.courtMicroServices.courtService.adapters.respositories;

import com.mxninja.courtMicroServices.courtService.domain.CourtTypeAggregation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 9/4/2018
 *
 * @author Mohammad Ali
 */

@Component
public class CourtTypeMongoRepository {

    private final CourtTypeDAO courtTypeDAO;

    @Autowired
    public CourtTypeMongoRepository(CourtTypeDAO courtTypeDAO) {
        this.courtTypeDAO = courtTypeDAO;
    }

    public CourtTypeProjection save(CourtTypeAggregation aggregation) {
        return courtTypeDAO.save(convertToProjection(aggregation));
    }

    private CourtTypeProjection convertToProjection(CourtTypeAggregation aggregation) {
        return new CourtTypeProjection(
                aggregation.getId() == null ? null : new ObjectId(aggregation.getId()),
                aggregation.getName(),
                aggregation.getImageUrl()
        );
    }

}
