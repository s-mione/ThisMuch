package com.smione.thismuch

import com.smione.thismuch.model.converter.AccessLogListElementAccessLogEntityConverter
import com.smione.thismuch.model.converter.InstantDurationStringConverter
import com.smione.thismuch.model.element.AccessLogListElement
import com.smione.thismuch.model.repository.entity.AccessLogEntity
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

class InstantCommonTestHelper {

    companion object {
        val INSTANT_TIME_ON: Instant = Instant.now().minus(3, ChronoUnit.HOURS)
        val INSTANT_TIME_OFF: Instant = Instant.now().minus(2, ChronoUnit.HOURS)

        val INSTANT_TIME_ON_LATER: Instant = Instant.now().minus(1, ChronoUnit.HOURS)
        val INSTANT_TIME_OFF_LATER: Instant = Instant.now()

        val TOTAL_TIME: Duration = Duration.ofHours(1)

        fun getAccessLogEntityList() = listOf(
            AccessLogEntity(
                InstantDurationStringConverter.fromInstantToString(
                    INSTANT_TIME_ON_LATER
                ),
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_OFF_LATER),
                InstantDurationStringConverter.fromDurationToString(TOTAL_TIME),
                1
            ),
            AccessLogEntity(
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_ON),
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_OFF),
                InstantDurationStringConverter.fromDurationToString(TOTAL_TIME),
                0
            )
        )

        fun accessLogElementList(accessLogEntityList: List<AccessLogEntity>): List<AccessLogListElement> {
            var index = accessLogEntityList.size
            return accessLogEntityList.map {
                AccessLogListElementAccessLogEntityConverter.fromAccessEntityToAccessListElementWithIndex(
                    index--, it
                )
            }
        }

        fun getAccessLogListElement(): AccessLogListElement =
            AccessLogListElement(0, INSTANT_TIME_ON, INSTANT_TIME_OFF, TOTAL_TIME)

        fun getAccessLogEntity(): AccessLogEntity =
            AccessLogEntity(
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_ON),
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_OFF),
                InstantDurationStringConverter.fromDurationToString(TOTAL_TIME),
                1
            )
    }

}