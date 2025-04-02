package com.smione.thismuch.utils.notification

import android.app.Notification
import android.content.Context
import androidx.fragment.app.Fragment
import com.smione.thismuch.R

class TimeNotificationUtils {

    companion object {
        private const val TIME_NOTIFICATION_ID = 1
        fun sendNotificationForTime(context: Context,
                                    channelFactory: NotificationChannelFactory,
                                    fragment: Fragment,
                                    hours: Int,
                                    minutes: Int) {
            val notification = createNotificationForTime(context, channelFactory, hours, minutes)
            NotificationUtils.sendNotification(
                context,
                fragment,
                TIME_NOTIFICATION_ID,
                notification
            )
        }

        fun createNotificationForTime(context: Context,
                                      channelFactory: NotificationChannelFactory,
                                      hours: Int,
                                      minutes: Int): Notification {
            return NotificationUtils.createNotification(
                context, channelFactory, getIconResource(hours, minutes),
                "Display on at:", "It's ${getFormattedTime(hours, minutes)}", true
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
}