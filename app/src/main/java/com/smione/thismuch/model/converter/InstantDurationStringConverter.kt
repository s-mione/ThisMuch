package com.smione.thismuch.model.converter

import java.time.Duration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class InstantDurationStringConverter {

    companion object {
        private val formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.systemDefault())

        fun fromInstantToString(instant: Instant): String {
            return instant.toString()
        }

        fun fromStringToInstant(string: String): Instant {
            return Instant.parse(string)
        }

        fun fromInstantToFormattedString(instant: Instant?): String {
            return formatter.format(instant)
        }

        fun fromDurationToString(duration: Duration): String {
            return duration.toString()
        }

        fun fromStringToDuration(string: String): Duration {
            return Duration.parse(string)
        }

        fun fromDurationToFormattedString(duration: Duration?): String {
            if (duration == null)
                return "00:00"
            val hours = duration.toHours()
            val minutes = duration.toMinutes() % 60
            val seconds = duration.seconds % 60
            return String.format("%02d:%02d:%02d", hours, minutes, seconds)
        }
    }
}