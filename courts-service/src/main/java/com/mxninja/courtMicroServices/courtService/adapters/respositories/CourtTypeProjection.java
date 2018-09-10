package com.mxninja.courtMicroServices.courtService.adapters.respositories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import java.util.UUID;

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

    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name ="UUID",
            strategy = "org.hibernate.id.UUIDGenerator")
    @Indexed(unique = true)
    private UUID uuid;

    private String name;

    private String imageUrl;

}
