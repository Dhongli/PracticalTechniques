package com.dai.technique.desensitization.util;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;

import java.io.IOException;
import java.util.Objects;

/**
 * @author daihongli
 * @version 1.0
 * @ClassName DesensitizationJsonSerializable
 * @Description: TODO
 * @Date 2024-06-02 13:35
 */
public class DesensitizationJsonSerializable extends JsonSerializer<String> implements ContextualSerializer {
    private DesensitizationStrategy desensitizationStrategy;
    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(desensitizationStrategy.getDesensitization().apply(value));
    }

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov, BeanProperty property) throws JsonMappingException {
        Desensitization annotation = property.getAnnotation(Desensitization.class);

        if (Objects.nonNull(annotation) && Objects.equals(String.class, property.getType().getRawClass())) {

            this.desensitizationStrategy = annotation.value();
            return this;
        }
        return prov.findValueSerializer(property.getType(), property);
    }
}
