package com.mxninja.courtMicroServices.courtService.domain;

import lombok.Data;

/**
 * 9/5/2018
 *
 * @author Mohammad Ali
 */

@Data
public class CourtDTO {

    private String typeId;
    private String typeName;
    private int count;

}
