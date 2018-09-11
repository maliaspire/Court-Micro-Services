package com.mxninja.courtMicroServices.models;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * 9/11/2018
 *
 * @author Mohammad Ali
 */

@Scope("prototype")
@Component
public class MySingleton {

    private String name;

    @Override
    public String toString() {
        return super.toString();
    }
}
