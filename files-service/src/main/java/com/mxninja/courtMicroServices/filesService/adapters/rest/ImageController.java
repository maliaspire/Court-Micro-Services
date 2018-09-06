package com.mxninja.courtMicroServices.filesService.adapters.rest;

import com.mxninja.courtMicroServices.filesService.adapters.respositories.BinFileMongoRepository;
import com.mxninja.courtMicroServices.filesService.adapters.respositories.BinFileProjection;
import com.mxninja.courtMicroServices.filesService.exceptions.FileNotFoundException;
import com.mxninja.courtMicroServices.models.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.awt.image.ToolkitImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

/**
 * 9/6/2018
 *
 * @author Mohammad Ali
 */

@Slf4j
@RestController
@RequestMapping("files")
public class ImageController {

    private final BinFileMongoRepository fileMongoRepository;
    private final int defWidth = 1500;
    private final int defHeight = 1500;

    @Autowired
    public ImageController(BinFileMongoRepository fileMongoRepository) {
        this.fileMongoRepository = fileMongoRepository;
    }

    @PostMapping(value = "/binData")
    public ResponseModel<ObjectId> saveFileBinData(@RequestBody MultipartFile multipartFile) throws IOException {
        long time = System.currentTimeMillis();
        BinFileProjection projection = fileMongoRepository.save(new BinFileProjection(null, multipartFile.getBytes()));
        log.debug(String.format("Save image as bin data: %d", System.currentTimeMillis() - time));
        return new ResponseModel<>(projection.getId());
    }

    @PostMapping(value = "/gridFs")
    public ResponseModel<String> saveFileGrid(@RequestBody MultipartFile multipartFile) throws IOException {
        long time = System.currentTimeMillis();

        String uuid = fileMongoRepository.saveGridFile(multipartFile.getBytes());
        log.debug(String.format("Save image as grid data: %dms", System.currentTimeMillis() - time));
        return new ResponseModel<>(uuid);
    }

    @PostMapping(value = "/binData/scale")
    public ResponseModel<ObjectId> saveFileBinDataScale(@RequestBody MultipartFile multipartFile) throws IOException {
        long time = System.currentTimeMillis();
        BinFileProjection projection = fileMongoRepository.save(new BinFileProjection(null, resizeImage(defWidth, defHeight, multipartFile.getBytes())));
        log.debug(String.format("Save image with scale w:%d h:%d as bin data: %d", defWidth, defHeight, System.currentTimeMillis() - time));
        return new ResponseModel<>(projection.getId());
    }

    @PostMapping(value = "/gridFs/scale")
    public ResponseModel<String> saveFileGridScale(@RequestBody MultipartFile multipartFile) throws IOException {
        long time = System.currentTimeMillis();
        String uuid = fileMongoRepository.saveGridFile(resizeImage(defWidth, defHeight, multipartFile.getBytes()));
        log.debug(String.format("Save image with scale w:%d h:%d as grid data: %dms", defWidth, defHeight, System.currentTimeMillis() - time));
        return new ResponseModel<>(uuid);
    }

    @GetMapping(value = "/binData/id/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getFileBinData(@PathVariable("id") String id) {
        long time = System.currentTimeMillis();
        Optional<BinFileProjection> optionalBinFileProjection = fileMongoRepository.findById(new ObjectId(id));
        if (!optionalBinFileProjection.isPresent()) {
            throw new FileNotFoundException();
        }
        BinFileProjection projection = optionalBinFileProjection.get();
        log.debug(String.format("Time to fetch the Document with BinData: %dms", System.currentTimeMillis() - time));
        return projection.getBytes();
    }

    @GetMapping(value = "/gridFs/id/{id}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getFileGridData(@PathVariable("id") String id) throws IOException {
        long time = System.currentTimeMillis();
        InputStream inputStream = fileMongoRepository.getGridFile(id);
        if (inputStream == null) {
            throw new FileNotFoundException();
        }
        byte[] bytes = IOUtils.toByteArray(inputStream);
        log.debug(String.format("Time to fetch the GridFs: %dms", System.currentTimeMillis() - time));
        return bytes;
    }

    @GetMapping(value = "/binData/id/{id}/width/{width}/height/{height}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getFileBinDataSize(
            @PathVariable("id") String id,
            @PathVariable("width") float width,
            @PathVariable("height") float height) throws IOException {
        long time = System.currentTimeMillis();
        Optional<BinFileProjection> optionalBinFileProjection = fileMongoRepository.findById(new ObjectId(id));
        if (!optionalBinFileProjection.isPresent()) {
            throw new FileNotFoundException();
        }
        BinFileProjection projection = optionalBinFileProjection.get();
        log.debug(String.format("Time to fetch the Document with BinData: %dms", System.currentTimeMillis() - time));
        return resizeImage(width, height, projection.getBytes());
    }

    @GetMapping(value = "/gridFs/id/{id}/width/{width}/height/{height}", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] getFileGridData(
            @PathVariable("id") String id,
            @PathVariable("width") float width,
            @PathVariable("height") float height) throws IOException {
        long time = System.currentTimeMillis();
        InputStream inputStream = fileMongoRepository.getGridFile(id);
        if (inputStream == null) {
            throw new FileNotFoundException();
        }
        byte[] bytes = IOUtils.toByteArray(inputStream);
        log.debug(String.format("Time to fetch the GridFs: %dms", System.currentTimeMillis() - time));
        return resizeImage(width, height, bytes);
    }

    private byte[] resizeImage(float newWidth, float newHeight, byte[] img) throws IOException {
        return resizeImage(newWidth, newHeight, ImageIO.read(new ByteArrayInputStream(img)));
    }

    private byte[] resizeImage(float newWidth, float newHeight, BufferedImage img) throws IOException {
        int sourceWidth = img.getWidth();
        int sourceHeight = img.getHeight();

        float nPercent;

        float nPercentW = newWidth / sourceWidth;
        float nPercentH = newHeight / sourceHeight;
        if (nPercentH < nPercentW) {
            nPercent = nPercentH;
        } else {
            nPercent = nPercentW;
        }

        int destWidth = (int) (sourceWidth * nPercent);
        int destHeight = (int) (sourceHeight * nPercent);
        long time = System.currentTimeMillis();
        BufferedImage imageBuff = getFasterScaledInstance(img, destWidth, destHeight, true);
        log.debug(String.format("Time to draw image: %dms", System.currentTimeMillis() - time));
        time = System.currentTimeMillis();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ImageIO.write(imageBuff, "jpg", buffer);
        log.debug(String.format("Time to write bytes: %dms", System.currentTimeMillis() - time));
        return buffer.toByteArray();
    }

    private BufferedImage getFasterScaledInstance(BufferedImage img, int targetWidth, int targetHeight, boolean progressiveBilinear) {
        /*int type = (img.getTransparency() == Transparency.OPAQUE) ? BufferedImage.TYPE_INT_RGB : BufferedImage.TYPE_INT_ARGB;*/
        int type = BufferedImage.TYPE_INT_RGB;
        BufferedImage ret = (BufferedImage) img;
        BufferedImage scratchImage = null;
        Graphics2D g2 = null;
        int w, h;
        int prevW = ret.getWidth();
        int prevH = ret.getHeight();
        if (progressiveBilinear) {
            w = img.getWidth();
            h = img.getHeight();
        } else {
            w = targetWidth;
            h = targetHeight;
        }
        do {
            if (progressiveBilinear && w > targetWidth) {
                w /= 2;
                if (w < targetWidth) {
                    w = targetWidth;
                }
            }

            if (progressiveBilinear && h > targetHeight) {
                h /= 2;
                if (h < targetHeight) {
                    h = targetHeight;
                }
            }

            if (scratchImage == null) {
                scratchImage = new BufferedImage(w, h, type);
                g2 = scratchImage.createGraphics();
            }
            g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2.drawImage(ret, 0, 0, w, h, 0, 0, prevW, prevH, null);
            prevW = w;
            prevH = h;
            ret = scratchImage;
        } while (w != targetWidth || h != targetHeight);

        g2.dispose();

        if (targetWidth != ret.getWidth() || targetHeight != ret.getHeight()) {
            scratchImage = new BufferedImage(targetWidth, targetHeight, type);
            g2 = scratchImage.createGraphics();
            g2.drawImage(ret, 0, 0, null);
            g2.dispose();
            ret = scratchImage;
        }
        return ret;
    }
}
