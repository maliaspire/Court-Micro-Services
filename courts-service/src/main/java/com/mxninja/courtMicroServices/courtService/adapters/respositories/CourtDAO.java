package com.mxninja.courtMicroServices.courtService.adapters.respositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 9/4/2018
 *
 * @author Mohammad Ali
 */
@Repository
interface CourtDAO extends MongoRepository<CourtProjection, ObjectId> {
}
