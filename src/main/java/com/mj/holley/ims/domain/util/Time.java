package com.mj.holley.ims.domain.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @author zhuhusheng
 * @date 2016/10/24
 */
public final class Time {
    public static ZoneId ZONE_ID = TimeZone.ASIA_SHANGHAI.getId();
    public static ZonedDateTime NOW = ZonedDateTime.now(ZONE_ID);
    public static Integer YEAR = NOW.getYear();
    public static Integer MONTH = NOW.getMonthValue();
    public static Integer DAY_OF_MONTH = NOW.getDayOfMonth();
    public static Integer DAY_OF_WEEK = NOW.getDayOfWeek().getValue();
    public static Integer HOUR = NOW.getHour();

    public static ZonedDateTime YESTERDAY = ZonedDateTime.now(ZONE_ID).minusDays(1);
    public static Integer LAST_YEAR = YESTERDAY.getYear();
    public static Integer LAST_MONTH = YESTERDAY.getMonthValue();
    public static Integer LAST_DAY_OF_MONTH = YESTERDAY.getDayOfMonth();
    public static Integer LAST_DAY_OF_WEEK = YESTERDAY.getDayOfWeek().getValue();
    public static Integer LAST_HOUR = YESTERDAY.getHour();

    public static ZonedDateTime getNOW() {
        return ZonedDateTime.now(TimeZone.ASIA_SHANGHAI.getId());
    }

    public static Integer getYEAR() {
        return getNOW().getYear();
    }

    public static Integer getMONTH() {
        return getNOW().getMonthValue();
    }

    public static Integer getDayOfMonth() {
        return getNOW().getDayOfMonth();
    }

    public static Integer getDayOfWeek() {
        return getNOW().getDayOfWeek().getValue();
    }

    public static Integer getHOUR() {
        return getNOW().getHour();
    }

    public static ZonedDateTime getYESTERDAY() {
        return getNOW().minusDays(1L);
    }

    public static Integer getLastYear() {
        return getYESTERDAY().getYear();
    }

    public static Integer getLastMonth() {
        return getYESTERDAY().getMonthValue();
    }

    public static Integer getLastDayOfMonth() {
        return getYESTERDAY().getDayOfMonth();
    }

    public static Integer getLastDayOfWeek() {
        return getYESTERDAY().getDayOfWeek().getValue();
    }

    public static Integer getLastHour() {
        return getYESTERDAY().getHour();
    }
}
