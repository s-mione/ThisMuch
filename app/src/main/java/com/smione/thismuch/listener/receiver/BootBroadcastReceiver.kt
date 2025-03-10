package com.smione.thismuch.listener.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.smione.thismuch.service.TimeNotificationService

class BootBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.v("BootBroadcastReceiver", "onReceive")
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED) {
            val serviceIntent = Intent(context, TimeNotificationService::class.java)
            context?.startService(serviceIntent)
        }
    }
}