package com.mxninja.courtMicroServices.filesService.adapters.rest;

import com.mxninja.courtMicroServices.filesService.command.TestCommand;
import com.mxninja.courtMicroServices.models.MySingleton;
import com.mxninja.courtMicroServices.models.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * 9/11/2018
 *
 * @author Mohammad Ali
 */

@RestController
@RequestMapping("test")
public class TestController {

    private static int value = 0;
    private final MySingleton singleton;

    @Autowired
    public TestController(MySingleton singleton) {
        this.singleton = singleton;
    }

    @GetMapping()
    public ResponseModel<Integer> getValue() {
        System.out.println(singleton);
        return new ResponseModel<>(++value);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseModel<Boolean> save(@RequestBody TestCommand body) {
        if (body.getName() == null || body.getName().isEmpty()) {
            throw new RuntimeException("test");
        }
        System.out.println(body.getName());
        return new ResponseModel<>(true);
    }

}
