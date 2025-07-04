package com.smione.thismuch.ui.dialog

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import java.util.Locale

class AskToIgnoreBatteryOptimizationDialog(private val context: Context) {

    fun show() {
        if (shouldAskToIgnoreBatteryOptimization()) {
            val manufacturer = Build.MANUFACTURER.lowercase(Locale.getDefault())

            val intent = when {
                manufacturer.contains("xiaomi") -> getXiaomiIntent()
                manufacturer.contains("huawei") -> getHuaweiIntent()
                manufacturer.contains("oppo") -> getOppoIntent()
                manufacturer.contains("vivo") -> getVivoIntent()
                manufacturer.contains("samsung") -> getSamsungIntent()
                else -> getDefaultIntent()
            }

            AlertDialog.Builder(context)
                .setTitle("Abilita l'AutoStart")
                .setMessage("Per garantire il corretto funzionamento dell'app, aggiungila alla lista delle app avviabili automaticamente nelle impostazioni del dispositivo.")
                .setPositiveButton("Vai alle Impostazioni") { _, _ ->
                    intent.let { context.startActivity(it) }
                }
                .setNegativeButton("Annulla", null)
                .setNeutralButton("Non mostrare più") { _, _ ->
                    val sharedPreferences =
                        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
                    with(sharedPreferences.edit()) {
                        putBoolean("ignore_battery_optimization_dialog_shown", true)
                        apply()
                    }
                }
                .show()
        }
    }

    private fun getXiaomiIntent(): Intent {
        return Intent().apply {
            component = ComponentName(
                "com.miui.securitycenter",
                "com.miui.permcenter.autostart.AutoStartManagementActivity"
            )
        }
    }

    private fun getHuaweiIntent(): Intent {
        return Intent().apply {
            component = ComponentName(
                "com.huawei.systemmanager",
                "com.huawei.systemmanager.optimize.process.ProtectActivity"
            )
        }
    }

    private fun getOppoIntent(): Intent {
        return Intent().apply {
            component = ComponentName(
                "com.coloros.safecenter",
                "com.coloros.safecenter.permission.startup.StartupAppListActivity"
            )
        }
    }

    private fun getVivoIntent(): Intent {
        return Intent().apply {
            component = ComponentName(
                "com.vivo.permissionmanager",
                "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"
            )
        }
    }

    private fun getSamsungIntent(): Intent {
        return Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = "package:${context.packageName}".toUri()
        }
    }

    private fun getDefaultIntent(): Intent {
        return Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = "package:${context.packageName}".toUri()
        }
    }

    private fun shouldAskToIgnoreBatteryOptimization(): Boolean {
        val sharedPreferences =
            context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        val ignoreDialog =
            sharedPreferences.getBoolean("ignore_battery_optimization_dialog_shown", false)
        return !ignoreDialog && !isIgnoringBatteryOptimizations(context)
    }

    private fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val packageName = context.packageName
        return powerManager.isIgnoringBatteryOptimizations(packageName)
    }
}
