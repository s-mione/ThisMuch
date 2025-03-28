package com.smione.thismuch.model.element

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class AccessLogListElement(var index: Int? = null,
                                var timeOn: Instant? = null,
                                var timeOff: Instant? = null,
                                var totalTime: Instant? = null) {
    companion object {
        private val formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.systemDefault())
    }

    fun formattedTimeOn(): String {
        return format(timeOn)
    }

    fun formattedTimeOff(): String {
        return format(timeOff)
    }

    fun formattedTotalTime(): String {
        return format(totalTime)
    }

    private fun format(time: Instant?): String {
        return time?.let {
            formatter.format(it)
        } ?: ""
    }

    override fun toString(): String {
        return "AccessLogListElement(index=$index, timeOn=$timeOn, timeOff=$timeOff, totalTime=$totalTime)"
    }
}