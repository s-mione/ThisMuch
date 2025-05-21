package com.smione.thismuch.listener.receiver

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.smione.thismuch.receivercontract.ScreenLockBroadcastReceiverContract
import timber.log.Timber


class ScreenLockBroadcastReceiver(private val screenLockBroadcastReceiverContract: ScreenLockBroadcastReceiverContract) :
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.v("ScreenLockBroadcastReceiver onReceive")
        if (intent?.action == Intent.ACTION_SCREEN_OFF) {
            this.screenLockBroadcastReceiverContract.onScreenLock()
        }
    }

    fun register(service: Service) {
        Timber.v("ScreenLockBroadcastReceiver register")
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF)
        service.registerReceiver(this, intentFilter)
    }
}