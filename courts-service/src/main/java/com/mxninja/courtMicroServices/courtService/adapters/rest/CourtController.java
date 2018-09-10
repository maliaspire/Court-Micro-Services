package com.mxninja.courtMicroServices.courtService.adapters.rest;

import com.mongodb.DBObject;
import com.mxninja.courtMicroServices.courtService.adapters.respositories.CourtMongoRepository;
import com.mxninja.courtMicroServices.courtService.adapters.respositories.CourtProjection;
import com.mxninja.courtMicroServices.courtService.domain.CourtAggregation;
import com.mxninja.courtMicroServices.courtService.domain.CourtDTO;
import com.mxninja.courtMicroServices.courtService.exceptions.CourtNotFoundException;
import com.mxninja.courtMicroServices.models.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

/**
 * 9/4/2018
 *
 * @author Mohammad Ali
 */

@Slf4j
@RestController
@RequestMapping("courts")
public class CourtController {

    private final CourtMongoRepository courtMongoRepository;

    public CourtController(CourtMongoRepository courtMongoRepository) {
        this.courtMongoRepository = courtMongoRepository;
    }

    @PostMapping
    public ResponseModel<CourtProjection> save(@RequestBody CourtAggregation aggregation) {
        return new ResponseModel<>(courtMongoRepository.save(aggregation));
    }

    @GetMapping("/type/group")
    public ResponseModel<List<CourtDTO>> groupByType() {
        return new ResponseModel<>(courtMongoRepository.groupByType());
    }

    @GetMapping
    public ResponseModel<List<DBObject>> getAll() {
        return new ResponseModel<>(courtMongoRepository.findAll());
    }

    @PutMapping("/id/{id}")
    public ResponseModel<CourtProjection> update(@PathVariable("id") String id, @RequestBody CourtAggregation aggregation) {
        Optional<CourtProjection> optional = courtMongoRepository.findById(new ObjectId(id));
        if (!optional.isPresent()) {
            throw new CourtNotFoundException();
        }
        aggregation.setId(id);
        return new ResponseModel<>(courtMongoRepository.update(aggregation));
    }

}
