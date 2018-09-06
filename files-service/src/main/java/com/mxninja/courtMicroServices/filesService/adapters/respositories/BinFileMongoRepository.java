package com.mxninja.courtMicroServices.filesService.adapters.respositories;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;

/**
 * 9/6/2018
 *
 * @author Mohammad Ali
 */

@Component
public class BinFileMongoRepository {

    private final BinFileDAO binFileDAO;
    private final GridFsTemplate gridFsTemplate;
    private final GridFSBucket gridFSBucket;

    @Autowired
    public BinFileMongoRepository(
            BinFileDAO binFileDAO,
            GridFsTemplate gridFsTemplate,
            GridFSBucket gridFSBucket) {
        this.binFileDAO = binFileDAO;
        this.gridFsTemplate = gridFsTemplate;
        this.gridFSBucket = gridFSBucket;
    }

    public BinFileProjection save(BinFileProjection projection) {
        return binFileDAO.save(projection);
    }

    public Optional<BinFileProjection> findById(ObjectId id) {
        return binFileDAO.findById(id);
    }

    public String saveGridFile(byte[] bytes) {
        String uuid = UUID.randomUUID().toString();
        gridFsTemplate.store(new ByteArrayInputStream(bytes), uuid, MediaType.IMAGE_JPEG_VALUE);
        return uuid;
    }

    public InputStream getGridFile(String uuid) throws IOException {
        Query query = new Query();
        query.addCriteria(Criteria.where("filename").is(uuid));
        GridFSFile gridFS = gridFsTemplate.findOne(query);
        if (gridFS == null) return null;
        GridFsResource resource = new GridFsResource(gridFS, gridFSBucket.openDownloadStream(gridFS.getId()));
        return resource.getInputStream();
    }
}
