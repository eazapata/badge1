package com.iesfbmoll.webScrapping.FileUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;


public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    private static final Logger log = LoggerFactory.getLogger(HashMapConverter.class);
    private ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME);
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(mapper.getTypeFactory());
        mapper.setAnnotationIntrospector(introspector);
        mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper;
    }


    @Override
    public String convertToDatabaseColumn(Map<String, Object> customerInfo) {

        String customerInfoJson = null;
        try {
            customerInfoJson = getMapper().writeValueAsString(customerInfo);
        } catch (final JsonProcessingException e) {
            log.error("JSON writing error", e);
        }

        return customerInfoJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String customerInfoJSON) {

        Map<String, Object> customerInfo = null;
        try {
            customerInfo = getMapper().readValue(customerInfoJSON, Map.class);
        } catch (final IOException e) {
            log.error("JSON reading error", e);
        }

        return customerInfo;
    }

}

