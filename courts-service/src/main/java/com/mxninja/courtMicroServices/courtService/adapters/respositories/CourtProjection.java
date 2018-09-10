package com.mxninja.courtMicroServices.courtService.adapters.respositories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

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
    @JsonIgnore
    public ObjectId id;
    @Indexed(unique = true)
    private UUID uuid;
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
