package com.mj.holley.ims.domain.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

/**
 * @author liumin
 */
public class DecimalPointFormatterDeserializer extends JsonDeserializer<String> {

    @Override
    public String deserialize(JsonParser p, DeserializationContext ctx) throws IOException {
        ObjectCodec objectCodec = p.getCodec();
        JsonNode node = objectCodec.readTree(p);
        String msDateString = node.asText();
        int pointIndex = msDateString.indexOf(".");
        if (pointIndex > -1) {
            msDateString = msDateString.substring(0, pointIndex);
        }
        return msDateString;
    }
}
