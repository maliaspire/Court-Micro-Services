package com.mxninja.courtMicroServices.courtService.adapters.respositories;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;

/**
 * 9/5/2018
 *
 * @author Mohammad Ali
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtServiceProjection {

    @Id
    @JsonIgnore
    @GeneratedValue
    private ObjectId id;
    private String name;
    private String iconUrl;
    private double price;
    private boolean multiUse;

}
