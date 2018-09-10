package com.mxninja.courtMicroServices.courtService.loader;

import com.mxninja.courtMicroServices.courtService.adapters.respositories.CourtTypeMongoRepository;
import com.mxninja.courtMicroServices.courtService.domain.CourtTypeAggregation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 9/9/2018
 *
 * @author Mohammad Ali
 */

@Component
public class Test implements CommandLineRunner {

    private final CourtTypeMongoRepository courtTypeMongoRepository;

    @Autowired
    public Test(CourtTypeMongoRepository repository) {
        this.courtTypeMongoRepository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        /*courtTypeMongoRepository.save(new CourtTypeAggregation(
                null,
                null,
                "Type 1",
                null
        ));*/
    }
}
