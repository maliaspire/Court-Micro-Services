package com.mxninja.courtMicroServices.courtService.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mxninja.courtMicroServices.courtService.adapters.respositories.CourtServiceProjection;
import lombok.Data;
import org.bson.types.ObjectId;

import java.util.UUID;

/**
 * 9/5/2018
 *
 * @author Mohammad Ali
 */

@Data
public class CourtServiceAggregation {

    @JsonIgnore
    private String id;
    private UUID uuid;
    private String name;
    @JsonIgnore
    private String iconUrl;
    private double price;
    private boolean multiUse;

    public static CourtServiceProjection convertToProjection(CourtServiceAggregation aggregation) {
        return new CourtServiceProjection(
                aggregation.getId() == null ? null : new ObjectId(aggregation.getId()),
                aggregation.getName(),
                aggregation.getIconUrl(),
                aggregation.getPrice(),
                aggregation.isMultiUse()
        );
    }

}
