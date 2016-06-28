package com.springapp.json;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Mathieu on 6/21/2016.
 */
public class JacksonUtil {

    private static final String CLASS = JacksonUtil.class.getName();
    protected static Logger log = Logger.getLogger(CLASS);

    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static <T> T deserialize(String json, Class<T> cl) {
        try {
            return mapper.readValue(json, cl);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Failed to deserialize json string: " + json, e);
            throw new JsonDeserializationException(cl, e);
        }
    }

    public static String serialize(Object json) {
        String result = null;
        try {
            result = mapper.writeValueAsString(json);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Failed to serialize json object: " + json, e);
            throw new RuntimeException("Invalid arguments.", e);
        }
        return result;
    }
}
