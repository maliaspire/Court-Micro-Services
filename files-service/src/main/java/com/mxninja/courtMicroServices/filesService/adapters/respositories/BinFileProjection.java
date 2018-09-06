package com.mxninja.courtMicroServices.filesService.adapters.respositories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;

/**
 * 9/6/2018
 *
 * @author Mohammad Ali
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinFileProjection {

    @Id
    @GeneratedValue
    private ObjectId id;

    private byte[] bytes;

}
