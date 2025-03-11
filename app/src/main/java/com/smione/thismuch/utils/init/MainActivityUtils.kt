package com.smione.thismuch.utils.init

import android.content.Context
import android.content.Intent
import android.util.Log
import com.smione.thismuch.receivercontract.ScreenUnlockBroadcastReceiverContract
import com.smione.thismuch.service.TimeNotificationService
import com.smione.thismuch.ui.dialog.AskToIgnoreBatteryOptimizationDialog

class MainActivityUtils {

    companion object {
        fun askToIgnoreBatteryOptimization(context: Context) {
            Log.v("MainActivityUtils", "askToIgnoreBatteryOptimization")
            AskToIgnoreBatteryOptimizationDialog(context).show()
        }

        fun startTimeNotificationServiceIfNotRunning(context: Context,
                                                     timeNotificationService: TimeNotificationService? = null,
                                                     subscriber: ScreenUnlockBroadcastReceiverContract? = null) {
            Log.v("MainActivityUtils", "startTimeNotificationServiceIfNotRunning")
            if (TimeNotificationService.isRunning.not()) {
                TimeNotificationService.isRunning = true
                val serviceIntent = Intent(context, TimeNotificationService::class.java)
                context.startService(serviceIntent)
            } else {
                subscriber?.let { timeNotificationService?.addUnlockReceiverSubscriber(it) }
            }
        }
    }
}