package com.mxninja.courtMicroServices.courtService.adapters.respositories;

import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.result.UpdateResult;
import com.mxninja.courtMicroServices.courtService.domain.CourtAggregation;
import com.mxninja.courtMicroServices.courtService.domain.CourtDTO;
import com.mxninja.courtMicroServices.courtService.domain.CourtServiceAggregation;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

/**
 * 9/4/2018
 *
 * @author Mohammad Ali
 */

@Component
public class CourtMongoRepository {

    private final CourtDAO courtDAO;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CourtMongoRepository(
            CourtDAO courtDAO,
            MongoTemplate mongoTemplate) {
        this.courtDAO = courtDAO;
        this.mongoTemplate = mongoTemplate;
    }

    public CourtProjection save(CourtAggregation aggregation) {
        return courtDAO.save(CourtAggregation.convertToProjection(aggregation));
    }

    public List<CourtDTO> groupByType() {
        AggregationOperation[] operations = new AggregationOperation[]{
                Aggregation.lookup("courtType", "courtTypeId", "_id", "types"),
                Aggregation.unwind("types"),
                Aggregation.project()
                        .and("types._id").as("typeId")
                        .and("types.name").as("typeName"),
                Aggregation.group("typeId", "typeName").count().as("count"),
                Aggregation.sort(Sort.Direction.DESC, "count")

        };
        return mongoTemplate.aggregate(Aggregation.newAggregation(operations), CourtProjection.class, CourtDTO.class).getMappedResults();
    }

    public List<DBObject> findAll() {
        AggregationOperation[] operations = new AggregationOperation[]{
                Aggregation.lookup("courtType", "courtTypeId", "_id", "type"),
                Aggregation.unwind("type"),

        };
        return mongoTemplate.aggregate(Aggregation.newAggregation(operations), CourtProjection.class, DBObject.class).getMappedResults();
    }

    public Optional<CourtProjection> findById(ObjectId id) {
        return courtDAO.findById(id);
    }

    public CourtProjection update(CourtAggregation aggregation) {
        return courtDAO.save(CourtAggregation.convertToProjection(aggregation));
    }

    public boolean addService(ObjectId id, CourtServiceAggregation aggregation) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").is(id));
        Update updateCommand = new Update();
        CourtServiceProjection serviceProjection = CourtServiceAggregation.convertToProjection(aggregation);
        serviceProjection.setId(new ObjectId());
        updateCommand.addToSet("servicesList", serviceProjection);
        UpdateResult result = mongoTemplate.updateFirst(query, updateCommand, CourtProjection.class);
        return result.getModifiedCount() > 0;
    }


}
