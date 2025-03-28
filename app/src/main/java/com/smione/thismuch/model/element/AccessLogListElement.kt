package com.smione.thismuch.model.element

import java.time.Instant

data class AccessLogListElement(var index: Int? = null,
                                var timeOn: Instant? = null,
                                var timeOff: Instant? = null,
                                var totalTime: Instant? = null) {
    override fun toString(): String {
        return "AccessLogListElement(index=$index, timeOn=$timeOn, timeOff=$timeOff, totalTime=$totalTime)"
    }
}