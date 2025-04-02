package com.smione.thismuch.utils.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

interface NotificationChannelFactory {

    fun defaultChannelId(): String
    fun defaultImportance(): Int

    fun createNotificationChannel(context: Context, channelName: String, channelDescription: String)
}

class DefaultNotificationChannelFactory : NotificationChannelFactory {

    companion object {
        private const val DEFAULT_CHANNEL_ID = "1"
        private const val IMPORTANCE_DEFAULT = NotificationManager.IMPORTANCE_DEFAULT
    }

    override fun defaultChannelId(): String {
        return DEFAULT_CHANNEL_ID
    }

    override fun defaultImportance(): Int {
        return IMPORTANCE_DEFAULT
    }

    override fun createNotificationChannel(context: Context,
                                           channelName: String,
                                           channelDescription: String) {
        val channel = NotificationChannel(
            this.defaultChannelId(), channelName, this.defaultImportance()
        ).apply {
            description = channelDescription
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}