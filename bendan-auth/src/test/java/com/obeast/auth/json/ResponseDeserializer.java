package com.obeast.auth.json;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Map;

public class ResponseDeserializer extends JsonDeserializer<Response> {
    @Override
    public Response deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        Map map = jsonParser.readValueAs(Map.class);
        Object headerMap = map.get("headerMap");
        Object apiMap = map.get("apiMap");
        return new Response(headerMap, apiMap);
    }
}