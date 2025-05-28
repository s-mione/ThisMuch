package com.smione.thismuch.notification.time

import android.app.Notification
import android.app.NotificationManager
import android.content.Context

class NotificationTimeShow() {

    fun showNotification(notificationId: Int, notification: Notification, context: Context) {
        val notificationManager: NotificationManager =
            context.getSystemService("notification") as NotificationManager
        notificationManager.notify(notificationId, notification)
    }
}