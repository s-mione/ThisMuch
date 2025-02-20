package com.smione.thismuch.converter

import com.smione.thismuch.repositorycontract.AccessLogEntity
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement
import java.time.Instant

class AccessLogListElementAccessLogEntityConverter {
    companion object {
        fun fromAccessListElementToAccessEntity(accessLogListElement: AccessLogListElement?): AccessLogEntity {
            return AccessLogEntity(
                timeOn = accessLogListElement?.formattedTimeOn,
                timeOff = accessLogListElement?.formattedTimeOff,
                totalTime = accessLogListElement?.formattedTotalTime
            )
        }

        fun fromAccessEntityToAccessListElement(accessEntry: AccessLogEntity): AccessLogListElement {
            return AccessLogListElement(
                Instant.parse(accessEntry.timeOn),
                Instant.parse(accessEntry.timeOff),
                Instant.parse(accessEntry.totalTime)
            )
        }
    }
}