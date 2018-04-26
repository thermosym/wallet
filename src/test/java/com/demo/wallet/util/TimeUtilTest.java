package com.demo.wallet.util;

import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static com.demo.wallet.util.TimeUtil.dbTsToRFC3339;
import static org.junit.Assert.*;

public class TimeUtilTest {

    @Test
    public void testDbTsToRFC3339() {
        LocalDateTime now = LocalDateTime.of(2018, 4, 1, 0, 0, 0);
        Timestamp timestamp = Timestamp.valueOf(now);
        String tsString = dbTsToRFC3339(timestamp, ZoneId.of("GMT+8"));
        assertEquals("2018-04-01T00:00:00+08:00", tsString);
    }
}