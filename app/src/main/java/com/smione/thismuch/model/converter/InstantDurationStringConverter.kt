package com.smione.thismuch.model.converter

import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class InstantDurationStringConverter {

    companion object {
        private val formatter =
            DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd").withZone(ZoneId.of("UTC"))

        fun fromInstantToString(instant: Instant): String =
            instant.toString()

        fun fromStringToInstant(string: String): Instant =
            runCatching { Instant.parse(string) }
                .getOrElse { throw IllegalArgumentException("Invalid String format: $string", it) }

        fun fromInstantToFormattedString(instant: Instant?): String =
            instant?.let { formatter.format(it) }
                ?: throw IllegalArgumentException("Instant cannot be null")

        fun fromDurationToString(duration: Duration): String =
            duration.toString()

        fun fromStringToDuration(string: String): Duration =
            runCatching { Duration.parse(string) }
                .getOrElse {
                    throw IllegalArgumentException("Invalid String format: $string", it)
                }

        fun fromDurationToFormattedString(duration: Duration?): String =
            duration?.let {
                val hours = duration.toHours()
                val minutes = duration.toMinutes() % 60
                val seconds = duration.seconds % 60
                String.format("%02d:%02d:%02d", hours, minutes, seconds)
            } ?: "00:00"
    }
}