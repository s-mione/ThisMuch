package com.smione.thismuch.utils.notification

import android.Manifest
import android.app.Notification
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment

class NotificationUtils {

    companion object {

        fun sendNotification(context: Context, fragment: Fragment, notificationId: Int,
                             notification: Notification) {
            with(NotificationManagerCompat.from(context)) {
                checkForPermission(context, fragment)
                notify(notificationId, notification)
            }
        }

        fun createNotification(context: Context,
                               channelFactory: NotificationChannelFactory,
                               iconId: Int,
                               title: String,
                               text: String,
                               onGoing: Boolean = false): Notification {
            channelFactory.createNotificationChannel(context, title, text)
            return NotificationCompat.Builder(context, channelFactory.defaultChannelId())
                .setSmallIcon(iconId)
                .setContentTitle(title)
                .setContentText(text)
                .setPriority(channelFactory.defaultImportance())
                .setOngoing(onGoing)
                .setChannelId(channelFactory.defaultChannelId())
                .build()
        }

        private fun checkForPermission(context: Context, fragment: Fragment) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS
                    )
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        fragment.requireActivity(),
                        arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1
                    )
                    return
                }
            }
        }
    }
}