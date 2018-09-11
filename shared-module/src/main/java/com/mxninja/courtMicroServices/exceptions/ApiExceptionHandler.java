package com.mxninja.courtMicroServices.exceptions;

import com.mxninja.courtMicroServices.models.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 9/5/2018
 *
 * @author Mohammad Ali
 */

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseModel> handleGeneralException(Exception ex) {
        ApiStatus status;
        if (ex instanceof ApiException) {
            status = ((ApiException) ex).getApiStatus();
        } else {
            status = ApiStatus.INTERNAL_SERVER_ERROR;
            log.error(ex.getMessage(), ex);
        }
        return new ResponseEntity<>(new ResponseModel(status), HttpStatus.OK);
    }
}
