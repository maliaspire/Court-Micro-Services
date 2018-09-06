package com.mxninja.courtMicroServices.filesService.adapters.respositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 9/6/2018
 *
 * @author Mohammad Ali
 */
@Repository
public interface BinFileDAO extends MongoRepository<BinFileProjection, ObjectId> {
}
