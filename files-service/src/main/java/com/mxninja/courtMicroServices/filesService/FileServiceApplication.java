package com.mxninja.courtMicroServices.filesService;

import com.mxninja.courtMicroServices.models.MySingleton;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

/**
 * 9/6/2018
 *
 * @author Mohammad Ali
 */

@SpringBootApplication
@ComponentScan("com.mxninja.courtMicroServices")
public class FileServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileServiceApplication.class, args);
    }

}
