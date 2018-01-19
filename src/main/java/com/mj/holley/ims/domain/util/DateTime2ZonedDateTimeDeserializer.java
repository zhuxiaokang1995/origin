package com.mj.holley.ims.domain.util;

import org.apache.commons.lang.StringUtils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

/**
 * @author zy
 *         Date: 16-11-20.
 *         2016-11-20 10:12:23 格式化成 ZonedDateTime
 */
public final class DateTime2ZonedDateTimeDeserializer {

    private static DateTimeFormatter ISO_DATE_OPTIONAL_TIME;

    static {
        ISO_DATE_OPTIONAL_TIME = new DateTimeFormatterBuilder()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .optionalStart()
            .appendLiteral(" ")
            .append(DateTimeFormatter.ISO_TIME)
            .toFormatter()
            .withZone(TimeZone.ASIA_SHANGHAI.getId());
    }

    public static ZonedDateTime deserialize(String timeString) {
        if (StringUtils.isBlank(timeString)) {
            return null;
        }
        return ZonedDateTime.parse(timeString, ISO_DATE_OPTIONAL_TIME);
    }
}
