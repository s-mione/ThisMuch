package com.smione.thismuch.model.element

import java.time.Duration
import java.time.Instant

data class AccessLogListElement(var index: Int,
                                var timeOn: Instant,
                                var timeOff: Instant,
                                var totalTime: Duration) {

    override fun toString(): String {
        return "AccessLogListElement(index=$index, timeOn=$timeOn, timeOff=$timeOff, totalTime=$totalTime)"
    }
}