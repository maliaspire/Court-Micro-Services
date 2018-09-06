package com.mxninja.courtMicroServices.courtService.exceptions;

import com.mxninja.courtMicroServices.exceptions.ApiException;
import com.mxninja.courtMicroServices.exceptions.ApiStatus;

/**
 * 9/5/2018
 *
 * @author Mohammad Ali
 */
public class CourtNotFoundException extends RuntimeException implements ApiException {

    public CourtNotFoundException() {
        super("Court not found");
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.NOT_FOUND;
    }
}
