package com.mxninja.courtMicroServices.oauthService.adapters.rest;

import com.mxninja.courtMicroServices.models.ResponseModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 9/11/2018
 *
 * @author Mohammad Ali
 */

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping()
    public ResponseModel<Boolean> test() {
        return new ResponseModel<>(true);
    }

}
