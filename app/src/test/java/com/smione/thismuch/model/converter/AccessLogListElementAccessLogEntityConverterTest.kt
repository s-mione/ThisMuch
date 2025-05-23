package com.smione.thismuch.model.converter

import com.smione.thismuch.InstantCommonTestHelper
import org.junit.Assert.assertEquals
import org.junit.Test

class AccessLogListElementAccessLogEntityConverterTest {

    @Test
    fun `test fromAccessListElementToAccessEntity`() {
        val accessLogListElement = InstantCommonTestHelper.getAccessLogListElement()
        val accessLogEntity =
            AccessLogListElementAccessLogEntityConverter.fromAccessListElementToAccessEntity(
                accessLogListElement
            )
        assertEquals(
            InstantDurationStringConverter.fromInstantToString(accessLogListElement.timeOn),
            accessLogEntity.timeOn
        )
        assertEquals(
            InstantDurationStringConverter.fromInstantToString(accessLogListElement.timeOff),
            accessLogEntity.timeOff
        )
        assertEquals(
            InstantDurationStringConverter.fromDurationToString(accessLogListElement.totalTime),
            accessLogEntity.totalTime
        )
    }

    @Test
    fun `test fromAccessEntityToAccessListElementWithIndex`() {
        val accessLogEntity = InstantCommonTestHelper.getAccessLogEntity()
        val accessLogListElement =
            AccessLogListElementAccessLogEntityConverter.fromAccessEntityToAccessListElementWithIndex(
                1,
                accessLogEntity
            )
        assertEquals(1, accessLogListElement.index)
        assertEquals(
            InstantDurationStringConverter.fromStringToInstant(accessLogEntity.timeOn),
            accessLogListElement.timeOn
        )
        assertEquals(
            InstantDurationStringConverter.fromStringToInstant(accessLogEntity.timeOff),
            accessLogListElement.timeOff
        )
        assertEquals(
            InstantDurationStringConverter.fromStringToDuration(accessLogEntity.totalTime),
            accessLogListElement.totalTime
        )
    }

}