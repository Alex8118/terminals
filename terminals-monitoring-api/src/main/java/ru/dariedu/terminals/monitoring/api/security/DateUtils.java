package ru.dariedu.terminals.monitoring.api.security;

import lombok.experimental.UtilityClass;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

@UtilityClass
public class DateUtils {

    /**
     * Gets timestamp from request and acceptableDelayHours
     * @param acceptableDelayHours contains the number of hours during which timestamp is valid
     * @param timestamp it's param from request
     * @return true, false
     */
    public static boolean isTimestampOutdated(long timestamp, int acceptableDelayHours) {
        return Instant.now().minus(acceptableDelayHours, ChronoUnit.HOURS).toEpochMilli() <= timestamp;
        }

}
