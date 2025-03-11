package com.smione.thismuch.model.converter

import java.time.Instant
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class InstantStringConverter {

    companion object {
        private const val TIME_FORMAT = "HH:mm"

        fun fromInstantToString(instant: Instant): String {
            val formatter = DateTimeFormatter.ofPattern(TIME_FORMAT)
            val localTime = instant.atZone(ZoneId.systemDefault()).toLocalTime()
            return formatter.format(localTime)
        }

        fun fromStringToInstant(string: String): Instant {
            val formatter = DateTimeFormatter.ofPattern(TIME_FORMAT)
            val localTime = LocalTime.parse(string, formatter)
            val localDateTime = localTime.atDate(LocalDate.now())
            return localDateTime.atZone(ZoneId.systemDefault()).toInstant()
        }
    }
}