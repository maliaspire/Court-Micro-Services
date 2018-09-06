package com.mxninja.courtMicroServices.components;

import com.mxninja.courtMicroServices.serializera.ToHexStringSerializer;
import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 9/5/2018
 *
 * @author Mohammad Ali
 */

@Component
public class CustomObjectMapper {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer getJackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.serializerByType(ObjectId.class, new ToHexStringSerializer());
    }

}
