package com.mxninja.courtMicroServices.courtService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 9/4/2018
 *
 * @author Mohammad Ali
 */

@SpringBootApplication
@ComponentScan("com.mxninja")
public class CourtServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CourtServiceApplication.class, args);
    }

}
