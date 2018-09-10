package com.mxninja.courtMicroServices.courtService.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mxninja.courtMicroServices.courtService.adapters.respositories.CourtProjection;
import com.mxninja.courtMicroServices.courtService.adapters.respositories.CourtServiceProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.types.ObjectId;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * 9/4/2018
 *
 * @author Mohammad Ali
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourtAggregation {

    @JsonIgnore
    private String id;
    private UUID uuid;
    private String name;
    private int size;
    private String courtTypeId;
    private LocalTime openTime;
    private LocalTime closeTime;
    private double price;
    private int minimumHoursReservation;
    private int maximumHoursReservation;
    private List<CourtServiceProjection> servicesList;
    private byte[] thumpImage;
    private String thumpImageBase64;

    public static CourtProjection convertToProjection(CourtAggregation aggregation) {
        return new CourtProjection(
                aggregation.getId() == null ? null : new ObjectId(aggregation.getId()),
                aggregation.getUuid(),
                aggregation.getName(),
                aggregation.getSize(),
                aggregation.getCourtTypeId() == null ? null : new ObjectId(aggregation.getCourtTypeId()),
                aggregation.getOpenTime(),
                aggregation.getCloseTime(),
                aggregation.getPrice(),
                aggregation.getMinimumHoursReservation(),
                aggregation.getMaximumHoursReservation(),
                aggregation.getServicesList(),
                aggregation.getThumpImage(),
                aggregation.getThumpImageBase64()
        );
    }
}
