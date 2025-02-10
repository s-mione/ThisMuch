package com.smione.thismuch.ui.fragment.recyclerview

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.ArrayList
import java.util.HashMap

class AccessListElement(val itemNumber: Int,
                        private val timeOn: Instant,
                        private val timeOff: Instant,
                        private val totalTime: Instant) {

    private val items: MutableList<AccessListElement> = ArrayList()

    private val itemMap: MutableMap<Int, AccessListElement> = HashMap()

    val formattedTimeOn: String
        get() = timeOn.toFormattedString()
    val formattedTimeOff: String
        get() = timeOff.toFormattedString()

    val formattedTotalTime: String
        get() = totalTime.toFormattedString()

    private fun Instant.toFormattedString(): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val localDateTime = LocalDateTime.ofInstant(this, ZoneId.systemDefault())
        return formatter.format(localDateTime)
    }

    private fun addItem(item: AccessListElement) {
        items.add(item)
        itemMap[item.itemNumber] = item
    }
}