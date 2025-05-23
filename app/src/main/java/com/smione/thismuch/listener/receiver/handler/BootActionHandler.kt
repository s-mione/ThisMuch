package com.smione.thismuch.listener.receiver.handler

import android.content.Context
import android.content.Intent
import com.smione.thismuch.service.TimeNotificationService

class BootActionHandler : BootActionHandlerInterface {

    override fun handleBoot(context: Context) {
        val serviceIntent = Intent(context, TimeNotificationService::class.java)
        context.startService(serviceIntent)
    }

}