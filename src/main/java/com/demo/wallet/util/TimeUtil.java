package com.demo.wallet.util;

import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.time.format.DateTimeFormatter.ISO_OFFSET_DATE_TIME;

public class TimeUtil {

    public static String dbTsToRFC3339(Timestamp timestamp, ZoneId toZone) {
        ZonedDateTime zdt = ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.of("GMT")).withZoneSameInstant(toZone);
        return zdt.format(ISO_OFFSET_DATE_TIME);
    }

}
