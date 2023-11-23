package com.alterjuice.utils

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

object DateTimeUtils {

    fun defaultZoneIDOffset()
        = ZoneId.systemDefault().rules.getOffset(Instant.now())

    fun getNormalizedStartDateToday(): Long {
        return LocalDate.now()
            .atStartOfDay()
            .toEpochSecond(defaultZoneIDOffset())
    }

    fun getNormalizedStartDate(timeSec: Long): Long {
        return LocalDateTime
            .ofEpochSecond(timeSec, 0, defaultZoneIDOffset())
            .toLocalDate()
            .atStartOfDay()
            .toEpochSecond(defaultZoneIDOffset())
    }
}

