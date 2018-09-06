package com.mxninja.courtMicroServices.courtService.adapters.respositories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 9/4/2018
 *
 * @author Mohammad Ali
 */


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "courtType")
public class CourtTypeProjection {

    @Id
    private ObjectId id;

    private String name;

    private String imageUrl;

}
