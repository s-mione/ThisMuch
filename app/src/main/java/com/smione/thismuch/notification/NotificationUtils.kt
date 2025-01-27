package com.smione.thismuch.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment

open class NotificationUtils {

    companion object {
        val DEFAULT_CHANNEL_ID = "1"

        fun sendNotification(context: Context, fragment: Fragment, notification: NotificationCompat.Builder) {
            with(NotificationManagerCompat.from(context)) {
                checkForPermission(context, fragment)
                notify(1, notification.build())
            }
        }

        fun createNotification(context: Context, channelId: String, iconId: Int, title: String,
                               text: String, priority: Int, onGoing: Boolean = false): NotificationCompat.Builder {
            return NotificationCompat.Builder(context, channelId)
                .setSmallIcon(iconId)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(priority)
                .setOngoing(onGoing)
        }

        fun createNotificationChannel(context: Context, channelId: String, channelName: String,
                                      channelDescription: String, importance: Int) {
            val channel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        private fun checkForPermission(context: Context, fragment: Fragment) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        fragment.requireActivity(),
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                        1
                    )
                    return
                }
            }
        }
    }
}