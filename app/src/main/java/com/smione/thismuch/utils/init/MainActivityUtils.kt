package com.smione.thismuch.utils.init

import android.content.Context
import android.content.Intent
import com.smione.thismuch.receivercontract.ScreenUnlockBroadcastReceiverContract
import com.smione.thismuch.service.timenotification.TimeNotificationService
import com.smione.thismuch.ui.dialog.AskToIgnoreBatteryOptimizationDialog
import timber.log.Timber

class MainActivityUtils {

    companion object {
        fun askToIgnoreBatteryOptimization(context: Context) {
            Timber.v("MainActivityUtils askToIgnoreBatteryOptimization")
            AskToIgnoreBatteryOptimizationDialog(context).show()
        }

        fun startTimeNotificationServiceIfNotRunning(context: Context,
                                                     timeNotificationService: TimeNotificationService? = null,
                                                     subscriber: ScreenUnlockBroadcastReceiverContract? = null) {
            Timber.v("MainActivityUtils startTimeNotificationServiceIfNotRunning")
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