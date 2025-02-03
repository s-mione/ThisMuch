package com.smione.thismuch.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.smione.thismuch.receivercontract.ScreenUnlockBroadcastReceiverContract

class ScreenUnlockBroadcastReceiver(private val screenUnlockBroadcastReceiverContract: ScreenUnlockBroadcastReceiverContract) :
    BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.v("ScreenUnlockBroadcastReceiver", "onReceive")
        this.screenUnlockBroadcastReceiverContract.onScreenUnlock()
    }

    fun register(context: Context) {
        Log.v("ScreenUnlockBroadcastReceiver", "register")
        IntentFilter(Intent.ACTION_SCREEN_ON).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(this, it, AppCompatActivity.RECEIVER_EXPORTED)
            } else {
                context.registerReceiver(this, it)
            }
        }
    }

}