package com.smione.thismuch.model.converter

import com.smione.thismuch.model.repository.accesslog.database.entity.AccessLogEntity
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement

class AccessLogListElementAccessLogEntityConverter {

    companion object {

        fun fromAccessListElementToAccessEntity(accessLogListElement: AccessLogListElement): AccessLogEntity {
            return AccessLogEntity(
                timeOn = accessLogListElement.timeOn.let {
                    InstantDurationStringConverter.fromInstantToString(it)
                },
                timeOff = accessLogListElement.timeOff.let {
                    InstantDurationStringConverter.fromInstantToString(it)
                },
                totalTime = accessLogListElement.totalTime.let {
                    InstantDurationStringConverter.fromDurationToString(it)
                }
            )
        }

        fun fromAccessEntityToAccessListElementWithIndex(index: Int,
                                                         accessEntry: AccessLogEntity): AccessLogListElement {
            return AccessLogListElement(
                index,
                accessEntry.timeOn.let { InstantDurationStringConverter.fromStringToInstant(it) },
                accessEntry.timeOff.let { InstantDurationStringConverter.fromStringToInstant(it) },
                accessEntry.totalTime.let { InstantDurationStringConverter.fromStringToDuration(it) },
            )
        }
    }
}