package com.mxninja.courtMicroServices.filesService.loaders;

import com.mxninja.courtMicroServices.filesService.adapters.respositories.BinFileMongoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * 9/9/2018
 *
 * @author Mohammad Ali
 */

@Slf4j
@Component
public class Test implements CommandLineRunner {

    private final BinFileMongoRepository repository;

    @Autowired
    public Test(BinFileMongoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) throws Exception {
        /*for (int i = 0; i < 100; i++) {
            File dir = new File("C:\\Users\\mali\\Downloads\\images");
            File[] listOfFiles = dir.listFiles();
            if (listOfFiles == null) {
                return;
            }
            for (File file : listOfFiles) {
                try {
                    log.debug(repository.saveGridFile(Files.readAllBytes(file.toPath())));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        log.debug("Done writing files");*/

    }
}
