package com.smione.thismuch.listener.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.smione.thismuch.listener.receiver.handler.BootActionHandler
import com.smione.thismuch.listener.receiver.handler.BootActionHandlerInterface
import timber.log.Timber

class BootBroadcastReceiver(val bootActionHandler: BootActionHandlerInterface = BootActionHandler()) :
    BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.v("BootBroadcastReceiver onReceive")
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            bootActionHandler.handleBoot(context)
        }
    }
}