package com.smione.thismuch.service

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.smione.thismuch.notification.TimeNotificationUtils
import com.smione.thismuch.receiver.ScreenUnlockBroadcastReceiver
import com.smione.thismuch.receivercontract.ScreenUnlockBroadcastReceiverContract
import java.time.Instant
import java.time.ZoneId

class TimeNotificationService : Service(), ScreenUnlockBroadcastReceiverContract {

    companion object {
        private const val NOTIFICATION_ID: Int = 1
    }

    private lateinit var notification: Notification
    private lateinit var screenUnlockBroadcastReceiver: ScreenUnlockBroadcastReceiver

    override fun onCreate() {
        Log.v("TimeNotificationService", "onCreate")
        super.onCreate()
        screenUnlockBroadcastReceiver = ScreenUnlockBroadcastReceiver(this)
        screenUnlockBroadcastReceiver.register(this)
        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(true)
        stopSelf()
    }

    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onScreenUnlock() {
        Log.v("TimeNotificationService", "onScreenUnlock")
        notification = buildNotification()
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun startForegroundService() {
        Log.v("TimeNotificationService", "startForegroundService")
        notification = buildNotification()
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun buildNotification(): Notification {
        val time: Instant = Instant.now()
        val hour = time.atZone(ZoneId.systemDefault()).hour
        val minute = time.atZone(ZoneId.systemDefault()).minute
        Log.v("TimeNotificationService", "buildNotification: at time [${hour}:${minute}]")
        return TimeNotificationUtils.createNotificationForTime(this, hour, minute)
    }
}