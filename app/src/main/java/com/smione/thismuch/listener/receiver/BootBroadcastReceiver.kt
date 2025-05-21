package com.smione.thismuch.listener.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.smione.thismuch.service.TimeNotificationService
import timber.log.Timber

class BootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.v("BootBroadcastReceiver onReceive")
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val serviceIntent = Intent(context, TimeNotificationService::class.java)
            context?.startService(serviceIntent)
        }
    }
}