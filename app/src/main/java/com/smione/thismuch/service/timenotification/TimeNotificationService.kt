package com.smione.thismuch.service.timenotification

import android.app.Notification
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.database.RoomAccessLogDatabaseProvider
import com.smione.thismuch.listener.receiver.ScreenLockBroadcastReceiver
import com.smione.thismuch.listener.receiver.ScreenUnlockBroadcastReceiver
import com.smione.thismuch.model.repository.accesslog.RoomAccessLogRepository
import com.smione.thismuch.notification.time.NotificationTimeCreate
import com.smione.thismuch.notification.time.NotificationTimeShow
import com.smione.thismuch.presenter.AccessLogRepositoryPresenter
import com.smione.thismuch.presenter.RuntimeDispatcherProvider
import com.smione.thismuch.presenter.RuntimeScopeProvider
import com.smione.thismuch.receivercontract.ScreenLockBroadcastReceiverContract
import com.smione.thismuch.receivercontract.ScreenUnlockBroadcastReceiverContract
import com.smione.thismuch.service.timenotification.receiver.TimeNotificationScreenLockBroadcastReceiverHandler
import com.smione.thismuch.service.timenotification.receiver.TimeNotificationScreenLockBroadcastReceiverHandlerInterface
import com.smione.thismuch.service.timenotification.receiver.TimeNotificationScreenUnlockBroadcastReceiverHandler
import com.smione.thismuch.service.timenotification.receiver.TimeNotificationScreenUnlockBroadcastReceiverHandlerInterface
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement
import timber.log.Timber
import java.time.Instant
import java.time.ZoneId

class TimeNotificationService(val timeNotificationScreenLockBroadcastReceiverHandler:
                              TimeNotificationScreenLockBroadcastReceiverHandlerInterface = TimeNotificationScreenLockBroadcastReceiverHandler(),
                              val timeNotificationScreenUnlockBroadcastReceiverHandler: TimeNotificationScreenUnlockBroadcastReceiverHandlerInterface = TimeNotificationScreenUnlockBroadcastReceiverHandler(),
                              val roomAccessLogRepositoryPresenter: AccessLogRepositoryContract.Presenter = AccessLogRepositoryPresenter(
                                  RuntimeScopeProvider(),
                                  RuntimeDispatcherProvider(),
                                  RoomAccessLogRepository(RoomAccessLogDatabaseProvider())
                              ),
                              val notificationTimeShow: NotificationTimeShow = NotificationTimeShow(),
                              val notificationTimeCreate: NotificationTimeCreate = NotificationTimeCreate()) :
    Service(), ScreenUnlockBroadcastReceiverContract,
    ScreenLockBroadcastReceiverContract, AccessLogRepositoryContract.View {

    companion object {
        private const val NOTIFICATION_ID: Int = 1

        private val temporaryAccessLogListElement =
            TimeNotificationScreenLockBroadcastReceiverHandlerInterface.TemporaryAccessLogListElement(
                null,
                null,
                null
            )

        var isRunning: Boolean = false
    }

    private lateinit var notification: Notification
    private lateinit var screenUnlockBroadcastReceiver: ScreenUnlockBroadcastReceiver
    private lateinit var screenLockBroadcastReceiver: ScreenLockBroadcastReceiver

    private val unlockReceiverSubscribers: MutableList<ScreenUnlockBroadcastReceiverContract> =
        mutableListOf()

    private val binder = LocalBinder()

    override fun onCreate() {
        Timber.Forest.v("TimeNotificationService onCreate")
        super.onCreate()

        roomAccessLogRepositoryPresenter.getAccessLogRepository.initDatabase(applicationContext)
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
        this.unregisterReceivers()
    }

    fun addUnlockReceiverSubscriber(subscriber: ScreenUnlockBroadcastReceiverContract) {
        unlockReceiverSubscribers.add(subscriber)
    }

    override fun onScreenUnlock() {
        Timber.Forest.v("TimeNotificationService onScreenUnlock")
        this.notifyUnlockReceivers()
        this.createAndShowNotification()

        this.timeNotificationScreenUnlockBroadcastReceiverHandler.onScreenUnlock(
            temporaryAccessLogListElement,
            roomAccessLogRepositoryPresenter
        )
    }

    override fun onScreenLock() {
        this.timeNotificationScreenLockBroadcastReceiverHandler.onScreenLock(
            temporaryAccessLogListElement,
            roomAccessLogRepositoryPresenter
        )
    }

    private fun buildNotification(): Notification {
        val time: Instant = Instant.now()
        val hour = time.atZone(ZoneId.systemDefault()).hour
        val minute = time.atZone(ZoneId.systemDefault()).minute
        Timber.Forest.v("TimeNotificationService buildNotification: at time [${hour}:${minute}]")
        return notificationTimeCreate.createNotification(this, hour, minute)
    }

    private fun createAndShowNotification() {
        notification = buildNotification()
        notificationTimeShow.showNotification(NOTIFICATION_ID, notification, this)
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

    private fun unregisterReceivers() {
        unregisterReceiver(screenUnlockBroadcastReceiver)
        unregisterReceiver(screenLockBroadcastReceiver)
    }

    private fun startForegroundService() {
        Timber.Forest.v("TimeNotificationService startForegroundService")
        notification = buildNotification()
        startForeground(NOTIFICATION_ID, notification)
        isRunning = true
    }

    inner class LocalBinder : Binder() {
        fun getService(): TimeNotificationService = this@TimeNotificationService
    }

    override fun onBind(intent: Intent?): IBinder {
        Timber.Forest.v("TimeNotificationService onBind")
        return binder
    }

    override fun onGetAccessLogList(accessLogList: List<AccessLogListElement>) {
        // Do nothing
    }

    override fun onDeleteAll() {
        // Do nothing
    }
}