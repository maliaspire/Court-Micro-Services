package com.mxninja.courtMicroServices.serializera;

import org.bson.types.ObjectId;
import org.springframework.core.serializer.Deserializer;

import java.io.IOException;
import java.io.InputStream;

/**
 * 9/5/2018
 *
 * @author Mohammad Ali
 */
public class ToHexStringDeserialize implements Deserializer<ObjectId> {

    @Override
    public ObjectId deserialize(InputStream inputStream) throws IOException {
        return null;
    }
}
