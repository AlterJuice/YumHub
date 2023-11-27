package com.alterjuice.utils

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

object DateTimeUtils {
    private const val dateSDFPattern = "yyyy-MM-dd"
    private const val dateShortSDFPattern = "MM-dd"
    private const val dateLongSDFPattern = "EEE, d MMMM, yyyy"
    private const val dateWithTimeSDFPattern = "MM-dd HH:mm"

    fun msToShortDateFormatted(timeMs: Long): String {
        return toDate(timeMs, dateShortSDFPattern)
    }
    fun msToDateFormattedWithTime(timeMs: Long): String {
        return toDate(timeMs, dateWithTimeSDFPattern)
    }

    fun msToDateFormatted(timeMs: Long): String {
        return toDate(timeMs, dateSDFPattern)
    }

    fun msToLongDateFormatted(timeMs: Long): String {
        return toDate(timeMs, dateLongSDFPattern)
    }

    fun secToLongDateFormatted(timeMs: Long): String {
        return msToLongDateFormatted(timeMs*1_000L)
    }

    fun secToShortDateFormatted(timeSec: Long): String {
        return msToShortDateFormatted(timeSec*1_000L)
    }
    fun secToDateFormattedWithTime(timeSec: Long): String {
        return msToDateFormattedWithTime(timeSec*1_000L)
    }

    fun secToDateFormatted(timeSec: Long): String {
        return msToDateFormatted(timeSec*1_000L)
    }

    private fun toDate(timeMs: Long, pattern: String): String {
        val dtShort = SimpleDateFormat(pattern, Locale.ENGLISH)
        return dtShort.format(Date(timeMs))
    }

    fun defaultZoneIDOffset()
        = ZoneId.systemDefault().rules.getOffset(Instant.now())

    fun getNormalizedStartDateTodaySec(): Long {
        return LocalDate.now()
            .atStartOfDay()
            .toEpochSecond(defaultZoneIDOffset())
    }
    fun getNormalizedStartDateTodayMs(): Long {
        return getNormalizedStartDateTodaySec().times(1000)
    }

    fun getCurrentTimeMillis(): Long {
        return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    fun getNormalizedStartDateFromSec(timeSec: Long): Long {
        return LocalDateTime
            .ofEpochSecond(timeSec, 0, defaultZoneIDOffset())
            .toLocalDate()
            .atStartOfDay()
            .toEpochSecond(defaultZoneIDOffset())
    }

    fun getNormalizedStartDateFromMsToSec(timeMs: Long): Long {
        return LocalDateTime
            .ofEpochSecond(timeMs.div(1000), 0, defaultZoneIDOffset())
            .toLocalDate()
            .atStartOfDay()
            .toEpochSecond(defaultZoneIDOffset())
    }

    fun getNormalizedStartDateFromMsToMs(timeMs: Long): Long {
        return LocalDateTime
            .ofEpochSecond(timeMs.div(1000), 0, defaultZoneIDOffset())
            .toLocalDate()
            .atStartOfDay()
            .toEpochSecond(defaultZoneIDOffset()).times(1000)
    }



    private fun getDurationFrom(
        days: Long, hours: Int, minutes: Int, seconds: Int, nanos: Int,
        forceAddHours: Boolean = false
    ) = buildString {
        val moreThenHalfOfSecond = nanos.nanoseconds.toInt(DurationUnit.MILLISECONDS) >= 500
        val additionalSecond = if (moreThenHalfOfSecond) {
            1
        } else 0
        if (days != 0L) append("$days days ")
        if (hours != 0 || days != 0L || forceAddHours)
            append("00$hours".takeLast(2) + ":")
        append("00$minutes".takeLast(2) + ":")
        append("00${seconds + additionalSecond}".takeLast(2))
    }

    fun getDurationFromMs(factTimeMs: Long): String {
        return factTimeMs.milliseconds.toComponents { days, hours, minutes, seconds, nanoseconds ->
            getDurationFrom(days, hours, minutes, seconds, nanoseconds)
        }
    }

    fun getDurationFromMs(factTimeMs: Float): String {
        return factTimeMs.toLong().milliseconds.toComponents { days, hours, minutes, seconds, nanoseconds ->
            getDurationFrom(days, hours, minutes, seconds, nanoseconds)
        }
    }

    fun getDurationFromSec(factTimeSec: Long?): String {
        if (factTimeSec == null) return "-"
        return factTimeSec.seconds.toComponents { days, hours, minutes, seconds, nanoseconds ->
            getDurationFrom(days, hours, minutes, seconds, nanoseconds)
        }
    }
}

