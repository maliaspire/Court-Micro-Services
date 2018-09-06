package com.mxninja.courtMicroServices.filesService.exceptions;

import com.mxninja.courtMicroServices.exceptions.ApiException;
import com.mxninja.courtMicroServices.exceptions.ApiStatus;

/**
 * 9/6/2018
 *
 * @author Mohammad Ali
 */
public class FileNotFoundException extends RuntimeException implements ApiException {

    public FileNotFoundException() {
        super("File not found");
    }

    @Override
    public ApiStatus getApiStatus() {
        return ApiStatus.NOT_FOUND;
    }
}
