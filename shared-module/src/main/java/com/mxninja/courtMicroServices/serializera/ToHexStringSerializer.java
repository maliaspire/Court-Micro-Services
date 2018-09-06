package com.mxninja.courtMicroServices.serializera;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 9/5/2018
 *
 * @author Mohammad Ali
 */

@Component
@JacksonStdImpl
@SuppressWarnings("serial")
public class ToHexStringSerializer extends StdSerializer<ObjectId> {

    public ToHexStringSerializer() {
        super(ObjectId.class);
    }

    @Override
    public boolean isEmpty(SerializerProvider provider, ObjectId value) {
        return value == null;
    }

    @Override
    public void serialize(ObjectId value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeString(value.toHexString());
    }
}
