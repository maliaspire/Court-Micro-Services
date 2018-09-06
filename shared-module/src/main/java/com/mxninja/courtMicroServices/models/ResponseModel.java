package com.mxninja.courtMicroServices.models;

import com.mxninja.courtMicroServices.exceptions.ApiStatus;
import lombok.Getter;

/**
 * 9/4/2018
 *
 * @author Mohammad Ali
 */

@Getter
public class ResponseModel<DataType> {

    private int code;
    private String message;
    private DataType data;

    private ResponseModel(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseModel(ApiStatus apiStatus) {
        this(apiStatus.getCode(), apiStatus.getMessage());
    }

    public ResponseModel(ApiStatus apiStatus, DataType data) {
        this(apiStatus);
        this.data = data;
    }

    public ResponseModel(DataType data) {
        this(ApiStatus.OK, data);
    }

    public ResponseModel() {
        this(ApiStatus.OK);
    }


}
