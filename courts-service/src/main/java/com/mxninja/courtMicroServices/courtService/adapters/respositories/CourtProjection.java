package com.mxninja.courtMicroServices.courtService.adapters.respositories;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.mxninja.courtMicroServices.courtService.domain.CourtAggregation;
import com.mxninja.courtMicroServices.serializera.ToHexStringSerializer;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.List;

/**
 * 9/4/2018
 *
 * @author Mohammad Ali
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "court")
public class CourtProjection {

    @Id
    public ObjectId id;
    private String name;
    private int size;
    private ObjectId courtTypeId;
    private LocalTime openTime;
    private LocalTime closeTime;
    private double price;
    private int minimumHoursReservation;
    private int maximumHoursReservation;
    private List<CourtServiceProjection> servicesList;
    private byte[] thumpImage;
    private String thumpImageBase64;

}
