package com.smione.thismuch.receiver

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import com.smione.thismuch.receivercontract.ScreenUnlockBroadcastReceiverContract


class ScreenUnlockBroadcastReceiver(private val screenUnlockBroadcastReceiverContract: ScreenUnlockBroadcastReceiverContract) :
    BroadcastReceiver() {


    override fun onReceive(context: Context?, intent: Intent?) {
        Log.v("ScreenUnlockBroadcastReceiver", "onReceive")
        if (intent?.action == Intent.ACTION_SCREEN_ON) {
            this.screenUnlockBroadcastReceiverContract.onScreenUnlock()
        }
    }

    fun register(service: Service) {
        Log.v("ScreenUnlockBroadcastReceiver", "register")
        val intentFilter = IntentFilter()
        intentFilter.addAction(Intent.ACTION_SCREEN_ON)
        service.registerReceiver(this, intentFilter)
    }
}