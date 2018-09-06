package com.mxninja.courtMicroServices.courtService.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 9/4/2018
 *
 * @author Mohammad Ali
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CourtTypeAggregation {

    @JsonIgnore
    private String id;
    @JsonIgnore
    private String uid;
    private String name;
    private String imageUrl;

}
