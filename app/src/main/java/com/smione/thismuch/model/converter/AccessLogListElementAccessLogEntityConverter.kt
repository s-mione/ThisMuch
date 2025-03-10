package com.smione.thismuch.model.converter

import com.smione.thismuch.model.repository.AccessLogEntity
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement

class AccessLogListElementAccessLogEntityConverter {
    companion object {
        fun fromAccessListElementToAccessEntity(accessLogListElement: AccessLogListElement?): AccessLogEntity {
            return AccessLogEntity(
                timeOn = accessLogListElement?.timeOn?.let {
                    InstantStringConverter.fromInstantToString(it)
                },
                timeOff = accessLogListElement?.timeOff?.let {
                    InstantStringConverter.fromInstantToString(it)
                },
                totalTime = accessLogListElement?.totalTime?.let {
                    InstantStringConverter.fromInstantToString(it)
                }
            )
        }

        fun fromAccessEntityToAccessListElement(accessEntry: AccessLogEntity): AccessLogListElement {
            return AccessLogListElement(
                accessEntry.timeOn?.let { InstantStringConverter.fromStringToInstant(it) },
                accessEntry.timeOff?.let { InstantStringConverter.fromStringToInstant(it) },
                accessEntry.totalTime?.let { InstantStringConverter.fromStringToInstant(it) },
            )
        }
    }
}