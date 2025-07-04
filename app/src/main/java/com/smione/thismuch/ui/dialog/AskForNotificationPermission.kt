package com.smione.thismuch.ui.dialog

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.appcompat.app.AlertDialog

class AskForNotificationPermission(private val context: Context) {

    fun show() {
        if (!shouldIgnoreTheAsking()) {
            val intent = Intent().apply {
                action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                data = Uri.fromParts("package", context.packageName, null)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }

            AlertDialog.Builder(context)
                .setTitle("Notification Permission Required")
                .setMessage("Please enable notification permissions for this app in the settings.")
                .setPositiveButton("Apri le impostazioni") { _, _ ->
                    context.startActivity(intent)
                }
                .setNegativeButton("Annulla", null)
                .setNeutralButton("Non mostrare più") { _, _ ->
                    val sharedPreferences =
                        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
                    with(sharedPreferences.edit()) {
                        putBoolean("ignore_ask_for_notification", true)
                        apply()
                    }
                }
                .show()
        }
    }

    private fun shouldIgnoreTheAsking(): Boolean {
        val sharedPreferences =
            context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val ignoreDialog =
            sharedPreferences.getBoolean("ignore_battery_optimization_dialog_shown", false)
        return ignoreDialog || notificationPermissionAlreadyAllowed()
    }

    private fun notificationPermissionAlreadyAllowed(): Boolean {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
        return notificationManager.areNotificationsEnabled()
    }
}
