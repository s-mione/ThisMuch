package com.smione.thismuch.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.smione.thismuch.receivercontract.ScreenLockUnlockBroadcastReceiverContract

class ScreenLockUnlockBroadcastReceiver(private val screenLockUnlockBroadcastReceiverContract: ScreenLockUnlockBroadcastReceiverContract): BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        this.screenLockUnlockBroadcastReceiverContract.onScreenLockUnlock()
    }

    fun register(context: Context) {
        IntentFilter(Intent.ACTION_SCREEN_ON).also {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                context.registerReceiver(this,it, AppCompatActivity.RECEIVER_EXPORTED)
            } else {
                context.registerReceiver(this,it)
            }
        }
    }

}