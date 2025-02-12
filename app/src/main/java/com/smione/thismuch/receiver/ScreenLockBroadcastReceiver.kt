package com.smione.thismuch.receiver

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.smione.thismuch.receivercontract.ScreenLockBroadcastReceiverContract


class ScreenLockBroadcastReceiver(private val screenLockBroadcastReceiverContract: ScreenLockBroadcastReceiverContract) :
    BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        Log.v("ScreenLockBroadcastReceiver", "onReceive")
        if (intent?.action == Intent.ACTION_SCREEN_OFF) {
            this.screenLockBroadcastReceiverContract.onScreenLock()
        }
    }

    fun register(service: Service) {
        Log.v("ScreenLockBroadcastReceiver", "register")
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        service.registerReceiver(this, intentFilter)
    }
}