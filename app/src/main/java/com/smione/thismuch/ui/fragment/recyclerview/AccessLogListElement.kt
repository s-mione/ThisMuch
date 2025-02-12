package com.smione.thismuch.ui.fragment.recyclerview

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class AccessLogListElement(var timeOn: Instant? = null,
                           var timeOff: Instant? = null,
                           var totalTime: Instant? = null) {

    companion object {
        private var temporaryElement: AccessLogListElement? = null
        fun getTemporaryElement(): AccessLogListElement? {
            return temporaryElement
        }

        fun saveScreenOn(timeOn: Instant) {
            temporaryElement = AccessLogListElement()
            temporaryElement?.timeOn = timeOn
        }

        fun saveScreenOff(timeOff: Instant) {
            temporaryElement?.timeOff = timeOff
            if (temporaryElement?.timeOn != null) {
                temporaryElement?.totalTime =
                    timeOff.minusSeconds(temporaryElement?.timeOn!!.epochSecond)
            }
        }

    }

    private val items: MutableList<AccessLogListElement> = ArrayList()

    private val itemMap: MutableMap<Int, AccessLogListElement> = HashMap()

    val formattedTimeOn: String?
        get() = timeOn?.toFormattedString()
    val formattedTimeOff: String?
        get() = timeOff?.toFormattedString()

    val formattedTotalTime: String?
        get() = totalTime?.toFormattedString()

    private fun Instant.toFormattedString(): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val localDateTime = LocalDateTime.ofInstant(this, ZoneId.systemDefault())
        return formatter.format(localDateTime)
    }

    private fun addItem(item: AccessLogListElement) {
        items.add(item)
        itemMap[items.size] = item
    }
}