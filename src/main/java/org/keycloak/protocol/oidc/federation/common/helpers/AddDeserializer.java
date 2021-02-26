package org.keycloak.protocol.oidc.federation.common.helpers;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.ContextualDeserializer;

public class AddDeserializer extends JsonDeserializer<Set<?>> implements ContextualDeserializer {
    private JavaType valueType;

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext ctxt, BeanProperty property)
        throws JsonMappingException {
        JavaType wrapperType = property.getType();
        AddDeserializer deserializer = new AddDeserializer();
        deserializer.valueType = wrapperType;
        return deserializer;
    }

    @Override
    public Set<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        if (jsonParser.isExpectedStartArrayToken()) {
            return (Set<?>) jsonParser.readValueAs(valueType.getRawClass());
        } else {
            return  Stream.of(jsonParser.readValueAs(valueType.containedType(0).getRawClass())).collect(Collectors.toSet());
        }
    }

}
