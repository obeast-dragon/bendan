package com.obeast.auth.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class ObjectMapperUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        SimpleModule simpleModule = new SimpleModule("custom");
        simpleModule.addDeserializer(Response.class, new ResponseDeserializer());
        objectMapper.registerModule(simpleModule);
    }

    public static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     *
     * @param object
     * @return
     */
    public static String writeAsPrettyString(Object object) {
        try {
            return getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}