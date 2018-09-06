package com.mxninja.courtMicroServices.exceptions;

import lombok.Getter;

/**
 * 9/4/2018
 *
 * @author Mohammad Ali
 */

@Getter
public enum ApiStatus {

    OK(200, "OK"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),;


    ApiStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

}
