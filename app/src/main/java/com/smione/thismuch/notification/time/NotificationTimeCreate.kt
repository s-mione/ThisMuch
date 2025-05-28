package com.smione.thismuch.notification.time

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.smione.thismuch.R
import com.smione.thismuch.utils.notification.NotificationUtils.Companion.DEFAULT_CHANNEL_ID
import com.smione.thismuch.utils.notification.NotificationUtils.Companion.createNotification

class NotificationTimeCreate() {

    fun createNotification(context: Context, hours: Int, minutes: Int): Notification {
        return createNotification(
            context, DEFAULT_CHANNEL_ID, getIconResource(hours, minutes),
            "Display on at:", "It's ${getFormattedTime(hours, minutes)}",
            NotificationCompat.PRIORITY_DEFAULT, true
        )
    }

    private fun getIconResource(hours: Int, minutes: Int): Int {
        val iconName = "ic_${String.format("%02d", hours)}${String.format("%02d", minutes)}"
        return try {
            val resField = R.drawable::class.java.getDeclaredField(iconName)
            resField.getInt(resField)
        } catch (e: Exception) {
            R.drawable.ic_0000
        }
    }

    private fun getFormattedTime(hours: Int, minutes: Int): String {
        return "${String.format("%02d", hours)}:${String.format("%02d", minutes)}"
    }
}