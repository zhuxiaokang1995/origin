package com.mj.holley.ims.domain.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zy
 */
public class DateSerializer extends JsonSerializer<Date> {

    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    public static final DateSerializer INSTANCE = new DateSerializer();

    private DateSerializer() {
    }

    @Override
    public void serialize(Date date, JsonGenerator generator,
                          SerializerProvider provider) throws IOException {
        String dateString = formatter.format(date);
        generator.writeString(dateString);
    }
}
