package com.mj.holley.ims.domain.util;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author zy
 * @date 2016/5/5
 */
public class ZonedDateTimeSerializer extends JsonSerializer<ZonedDateTime> {

    public static final ZonedDateTimeSerializer INSTANCE = new ZonedDateTimeSerializer();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        String formattedDate = zonedDateTime.format(formatter);
        jsonGenerator.writeString(formattedDate);
    }
}
