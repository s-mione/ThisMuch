package com.smione.thismuch.listener.receiver

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import com.smione.thismuch.receivercontract.ScreenUnlockBroadcastReceiverContract
import timber.log.Timber


class ScreenUnlockBroadcastReceiver(private val screenUnlockBroadcastReceiverContract: ScreenUnlockBroadcastReceiverContract) :
    BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        Timber.v("ScreenUnlockBroadcastReceiver onReceive")
        if (intent?.action == Intent.ACTION_SCREEN_ON) {
            this.screenUnlockBroadcastReceiverContract.onScreenUnlock()
        }
    }

    fun register(service: Service) {
        Timber.v("ScreenUnlockBroadcastReceiver register")
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        service.registerReceiver(this, intentFilter)
    }
}