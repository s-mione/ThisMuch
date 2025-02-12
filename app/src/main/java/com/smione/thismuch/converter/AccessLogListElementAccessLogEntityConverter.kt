package com.smione.thismuch.converter

import com.smione.thismuch.repositorycontract.AccessLogEntity
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement

class AccessLogListElementAccessLogEntityConverter {
    companion object {
        fun fromAccessListElementToAccessEntity(accessLogListElement: AccessLogListElement?): AccessLogEntity {
            return AccessLogEntity(
                timeOn = accessLogListElement?.timeOn,
                timeOff = accessLogListElement?.timeOff,
                totalTime = accessLogListElement?.totalTime
            )
        }

        fun fromAccessEntityToAccessListElement(accessEntry: AccessLogEntity): AccessLogListElement {
            return AccessLogListElement(
                accessEntry.timeOn,
                accessEntry.timeOff,
                accessEntry.totalTime
            )
        }
    }
}