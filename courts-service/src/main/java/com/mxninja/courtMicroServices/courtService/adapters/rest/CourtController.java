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

    @PostMapping("/image/id/{id}")
    public ResponseModel<Boolean> addImage(
            @RequestBody MultipartFile multipartFile,
            @PathVariable("id") String id) {
        Optional<CourtProjection> optional = courtMongoRepository.findById(new ObjectId(id));
        if (!optional.isPresent()) {
            throw new CourtNotFoundException();
        }
        try {
            byte[] imageBytes = multipartFile.getBytes();
            CourtProjection courtProjection = optional.get();
            courtProjection.setThumpImage(imageBytes);
            courtMongoRepository.saveImage(courtProjection.getId(), imageBytes);
            courtMongoRepository.update(courtProjection);
            return new ResponseModel<>(true);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseModel<>(false);
        }
    }

    @GetMapping(value = "/image/id/{id}", produces = MediaType.IMAGE_JPEG_VALUE, consumes = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageBytes(@PathVariable("id") String id) {
        Optional<CourtProjection> optional = courtMongoRepository.findById(new ObjectId(id));
        if (!optional.isPresent()) {
            throw new CourtNotFoundException();
        }

        CourtProjection courtProjection = optional.get();
        return courtProjection.getThumpImage();
    }

    @GetMapping(value = "/image64/id/{id}", produces = MediaType.IMAGE_JPEG_VALUE, consumes = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImageBytes64(@PathVariable("id") String id) {
        Optional<CourtProjection> optional = courtMongoRepository.findById(new ObjectId(id));
        if (!optional.isPresent()) {
            throw new CourtNotFoundException();
        }

        CourtProjection courtProjection = optional.get();
        return Base64.getDecoder().decode(courtProjection.getThumpImageBase64());
    }

    @GetMapping(value = "/image/id/{id}/size/width/{width}/height/{height}",
            produces = MediaType.IMAGE_JPEG_VALUE,
            consumes = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getSizeImage(
            @PathVariable("id") String id,
            @PathVariable("width") int newWidth,
            @PathVariable("height") int newHeight) throws IOException {
        Optional<CourtProjection> optional = courtMongoRepository.findById(new ObjectId(id));
        if (!optional.isPresent()) {
            throw new CourtNotFoundException();
        }

        CourtProjection courtProjection = optional.get();
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(courtProjection.getThumpImage()));
        return resizeImage(newWidth, newHeight, img);
    }

    @GetMapping(value = "/imageFs/id/{id}/size/width/{width}/height/{height}",
            produces = MediaType.IMAGE_JPEG_VALUE,
            consumes = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getSizeImageFs(
            @PathVariable("id") String id,
            @PathVariable("width") int newWidth,
            @PathVariable("height") int newHeight) throws IOException {
        Optional<CourtProjection> optional = courtMongoRepository.findById(new ObjectId(id));
        if (!optional.isPresent()) {
            throw new CourtNotFoundException();
        }

        CourtProjection courtProjection = optional.get();
        BufferedImage img = ImageIO.read(courtMongoRepository.getImage(courtProjection.getId()));
        return resizeImage(newWidth, newHeight, img);
    }

    private byte[] resizeImage(float newWidth, float newHeight, BufferedImage img) throws IOException {
        int sourceWidth = img.getWidth();
        int sourceHeight = img.getHeight();
        /*int sourceX = 0;
        int sourceY = 0;
        int destX = 0;
        int destY = 0;*/

        float nPercent;

        float nPercentW = newWidth / sourceWidth;
        float nPercentH = newHeight / sourceHeight;
        if (nPercentH < nPercentW) {
            nPercent = nPercentH;
            /*destX = (int) (newWidth -
                    (sourceWidth * nPercent)) / 2;*/
        } else {
            nPercent = nPercentW;
            /*destY = (int) (newHeight -
                    (sourceHeight * nPercent)) / 2;*/
        }

        int destWidth = (int) (sourceWidth * nPercent);
        int destHeight = (int) (sourceHeight * nPercent);
        long time = System.currentTimeMillis();
        Image scaledImage = img.getScaledInstance(destWidth, destHeight, Image.SCALE_SMOOTH);
        log.debug(String.format("Time to scale image: %d", System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        BufferedImage imageBuff = new BufferedImage(destWidth, destHeight, BufferedImage.TYPE_INT_RGB);
        imageBuff.getGraphics().drawImage(scaledImage, 0, 0, Color.BLACK, null);
        log.debug(String.format("Time to draw image: %d", System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ImageIO.write(imageBuff, "jpg", buffer);
        log.debug(String.format("Time to write bytes: %d", System.currentTimeMillis() - time));
        /*WritableRaster raster = imageBuff.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
        return data.getData();*/
        return buffer.toByteArray();
    }

}
