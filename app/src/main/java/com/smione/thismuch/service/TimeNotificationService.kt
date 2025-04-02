package com.smione.thismuch.service

import android.app.Notification
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.listener.receiver.ScreenLockBroadcastReceiver
import com.smione.thismuch.listener.receiver.ScreenUnlockBroadcastReceiver
import com.smione.thismuch.model.element.AccessLogListElement
import com.smione.thismuch.model.repository.RoomAccessLogRepository
import com.smione.thismuch.presenter.AccessLogRepositoryPresenter
import com.smione.thismuch.presenter.RuntimeDispatcherProvider
import com.smione.thismuch.receivercontract.ScreenLockBroadcastReceiverContract
import com.smione.thismuch.receivercontract.ScreenUnlockBroadcastReceiverContract
import com.smione.thismuch.utils.notification.DefaultNotificationChannelFactory
import com.smione.thismuch.utils.notification.TimeNotificationUtils
import java.time.Duration
import java.time.Instant
import java.time.ZoneId

class TimeNotificationService : Service(), ScreenUnlockBroadcastReceiverContract,
    ScreenLockBroadcastReceiverContract, AccessLogRepositoryContract.View {

    companion object {
        private const val NOTIFICATION_ID: Int = 1

        private var temporaryAccessLogListElement: AccessLogListElement = AccessLogListElement()

        var isRunning: Boolean = false
    }

    private lateinit var notification: Notification
    private lateinit var screenUnlockBroadcastReceiver: ScreenUnlockBroadcastReceiver
    private lateinit var screenLockBroadcastReceiver: ScreenLockBroadcastReceiver

    private lateinit var roomAccessLogRepositoryPresenter: AccessLogRepositoryContract.Presenter

    private val unlockReceiverSubscribers: MutableList<ScreenUnlockBroadcastReceiverContract> =
        mutableListOf()

    private val binder = LocalBinder()

    override fun onCreate() {
        Log.v("TimeNotificationService", "onCreate")
        super.onCreate()

        roomAccessLogRepositoryPresenter =
            AccessLogRepositoryPresenter(RuntimeDispatcherProvider(), RoomAccessLogRepository(this))
        roomAccessLogRepositoryPresenter.bindView(this)

        this.registerReceivers()

        startForegroundService()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
        isRunning = false
    }

    fun addUnlockReceiverSubscriber(subscriber: ScreenUnlockBroadcastReceiverContract) {
        unlockReceiverSubscribers.add(subscriber)
    }

    override fun onScreenUnlock() {
        Log.v("TimeNotificationService", "onScreenUnlock")
        this.notifyUnlockReceivers()

        this.createAndShowNotification()

        this.saveAccessLogEntityIfFilled(true)
        setTemporaryAccessLogListElementValuesWhenUnlock()
    }

    override fun onScreenLock() {
        Log.v("TimeNotificationService", "onScreenLock")
        setTemporaryAccessLogListElementValuesWhenLock()
        this.saveAccessLogEntityIfFilled()
    }

    private fun calculateTotalTime(timeOn: Instant, timeOff: Instant): Duration {
        val duration = Duration.between(timeOn, timeOff)
        Log.v(
            "TimeNotificationService",
            "calculateTotalTime: timeOn $timeOn, timeOff $timeOff, duration $duration"
        )
        return duration
    }

    private fun saveAccessLogEntityIfFilled(resetAlsoIfNotSaved: Boolean = false) {
        if (this.checkIfTemporaryAccessLogEntityIsToSave()) {
            Log.d(
                "TimeNotificationService",
                "saveAccessLogEntityIfFilled: saving temporary element $temporaryAccessLogListElement"
            )
            this.roomAccessLogRepositoryPresenter.saveAccessLogElement(temporaryAccessLogListElement)
            temporaryAccessLogListElement = AccessLogListElement()
        } else {
            if (resetAlsoIfNotSaved) {
                temporaryAccessLogListElement = AccessLogListElement()
            }
        }
    }

    private fun checkIfTemporaryAccessLogEntityIsToSave(): Boolean {
        Log.d(
            "TimeNotificationService",
            "checkIfTemporaryAccessLogEntityIsToSave: temporary element $temporaryAccessLogListElement"
        )
        return temporaryAccessLogListElement.timeOn != null && temporaryAccessLogListElement.timeOff != null && temporaryAccessLogListElement.totalTime != null
    }

    private fun buildNotification(): Notification {
        val time: Instant = Instant.now()
        val hour = time.atZone(ZoneId.systemDefault()).hour
        val minute = time.atZone(ZoneId.systemDefault()).minute
        Log.v("TimeNotificationService", "buildNotification: at time [${hour}:${minute}]")
        return TimeNotificationUtils.createNotificationForTime(
            this,
            DefaultNotificationChannelFactory(), hour, minute
        )
    }

    private fun setTemporaryAccessLogListElementValuesWhenUnlock() {
        temporaryAccessLogListElement.timeOn = Instant.now()
    }

    private fun setTemporaryAccessLogListElementValuesWhenLock() {
        if (temporaryAccessLogListElement.timeOn != null) {
            temporaryAccessLogListElement.timeOff = Instant.now()

            temporaryAccessLogListElement.totalTime = this.calculateTotalTime(
                temporaryAccessLogListElement.timeOn!!,
                temporaryAccessLogListElement.timeOff!!
            )
        }
    }

    private fun createAndShowNotification() {
        notification = buildNotification()
        val notificationManager: NotificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun notifyUnlockReceivers() {
        for (subscriber in unlockReceiverSubscribers) {
            subscriber.onScreenUnlock()
        }
    }

    private fun registerReceivers() {
        screenUnlockBroadcastReceiver = ScreenUnlockBroadcastReceiver(this)
        screenUnlockBroadcastReceiver.register(this)

        screenLockBroadcastReceiver = ScreenLockBroadcastReceiver(this)
        screenLockBroadcastReceiver.register(this)
    }

    private fun startForegroundService() {
        Log.v("TimeNotificationService", "startForegroundService")
        notification = buildNotification()
        startForeground(NOTIFICATION_ID, notification)
        isRunning = true
    }

    inner class LocalBinder : Binder() {
        fun getService(): TimeNotificationService = this@TimeNotificationService
    }

    override fun onBind(intent: Intent?): IBinder {
        Log.v("TimeNotificationService", "onBind")
        return binder
    }

    override fun onGetAccessLogList(accessLogList: List<AccessLogListElement>) {
        // Do nothing
    }

    override fun onDeleteAll() {
        // Do nothing
    }
}