package com.smione.thismuch.model.converter

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test
import java.time.Duration
import java.time.Instant

class InstantDurationStringConverterTest {

    @Test
    fun `test fromInstantToString`() {
        val instant = Instant.parse("2023-10-01T10:00:00Z")
        val result = InstantDurationStringConverter.fromInstantToString(instant)
        assertEquals("2023-10-01T10:00:00Z", result)
    }

    @Test
    fun `test fromStringToInstant`() {
        val string = "2023-10-01T10:00:00Z"
        val result = InstantDurationStringConverter.fromStringToInstant(string)
        assertEquals(Instant.parse(string), result)
    }

    @Test
    fun `test fromStringToInstant with an invalid string`() {
        val string = "invalid time"
        val exception = assertThrows(IllegalArgumentException::class.java) {
            InstantDurationStringConverter.fromStringToInstant(string)
        }
        assertEquals("Invalid String format: invalid time", exception.message)
    }

    @Test
    fun `test fromInstantToFormattedString`() {
        val instant = Instant.parse("2023-10-01T10:00:00Z")
        val result = InstantDurationStringConverter.fromInstantToFormattedString(instant)
        assertEquals("2023-10-01 10:00", result)
    }

    @Test
    fun `test fromInstantToFormattedString with null instant`() {
        val instant: Instant? = null
        val exception = assertThrows(IllegalArgumentException::class.java) {
            InstantDurationStringConverter.fromInstantToFormattedString(instant)
        }
        assertEquals("Instant cannot be null", exception.message)
    }


    @Test
    fun `test fromDurationToString`() {
        val duration = Duration.ofHours(1).plusMinutes(30).plusSeconds(45)
        val result = InstantDurationStringConverter.fromDurationToString(duration)
        assertEquals("PT1H30M45S", result)
    }

    @Test
    fun `test fromStringToDuration`() {
        val duration = Duration.ofHours(1).plusMinutes(30).plusSeconds(45)
        val result = InstantDurationStringConverter.fromStringToDuration("PT1H30M45S")
        assertEquals(duration, result)
    }

    @Test
    fun `test fromStringToDuration with an invalid string`() {
        val string = "invalid duration"
        val exception = assertThrows(IllegalArgumentException::class.java) {
            InstantDurationStringConverter.fromStringToDuration(string)
        }
        assertEquals("Invalid String format: invalid duration", exception.message)
    }

    @Test
    fun `test fromDurationToFormattedString`() {
        val duration = Duration.ofHours(1).plusMinutes(30).plusSeconds(45)
        val result = InstantDurationStringConverter.fromDurationToFormattedString(duration)
        assertEquals("01:30:45", result)
    }

    @Test
    fun `test fromDurationToFormattedString with null duration`() {
        val duration: Duration? = null
        val result = InstantDurationStringConverter.fromDurationToFormattedString(duration)
        assertEquals("00:00", result)
    }
}