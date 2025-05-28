package com.smione.thismuch.service.timenotification

import android.app.Notification
import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.notification.time.NotificationTimeCreate
import com.smione.thismuch.notification.time.NotificationTimeShow
import com.smione.thismuch.receivercontract.ScreenUnlockBroadcastReceiverContract
import com.smione.thismuch.service.timenotification.receiver.TimeNotificationScreenLockBroadcastReceiverHandlerInterface
import com.smione.thismuch.service.timenotification.receiver.TimeNotificationScreenLockBroadcastReceiverHandlerInterface.TemporaryAccessLogListElement
import com.smione.thismuch.service.timenotification.receiver.TimeNotificationScreenUnlockBroadcastReceiverHandlerInterface
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import java.time.Instant
import java.time.ZoneId

class TimeNotificationServiceTest {

    @MockK
    private lateinit var timeNotificationScreenLockBroadcastReceiverHandler: TimeNotificationScreenLockBroadcastReceiverHandlerInterface

    @MockK
    private lateinit var timeNotificationScreenUnlockBroadcastReceiverHandler: TimeNotificationScreenUnlockBroadcastReceiverHandlerInterface

    @MockK
    private lateinit var roomAccessLogRepositoryPresenter: AccessLogRepositoryContract.Presenter

    @MockK
    private lateinit var notificationTimeShow: NotificationTimeShow

    @MockK
    private lateinit var notificationTimeCreate: NotificationTimeCreate

    private lateinit var timeNotificationService: TimeNotificationService

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        timeNotificationService = TimeNotificationService(
            timeNotificationScreenLockBroadcastReceiverHandler,
            timeNotificationScreenUnlockBroadcastReceiverHandler,
            roomAccessLogRepositoryPresenter,
            notificationTimeShow,
            notificationTimeCreate
        )
    }

    @Test
    fun testAddUnlockReceiverSubscriber() {
        val subscriber = mockk<ScreenUnlockBroadcastReceiverContract>()

        timeNotificationService.addUnlockReceiverSubscriber(subscriber)

    }

    @Test
    fun testOnScreenLock() {

        stubTimeNotificationScreenLockBroadcastReceiverHandler(getTemporaryAccessLogListElement())

        timeNotificationService.onScreenLock()

        verify { timeNotificationScreenLockBroadcastReceiverHandler.onScreenLock(any(), any()) }
    }

    @Test
    fun testOnScreenUnlock() {

        stubNotificationTimeShow()

        val time: Instant = Instant.now()
        val hour = time.atZone(ZoneId.systemDefault()).hour
        val minute = time.atZone(ZoneId.systemDefault()).minute
        stubNotificationTimeCreate(hour, minute)

        stubTimeNotificationScreenUnlockBroadcastReceiverHandler()

        timeNotificationService.onScreenUnlock()

        verify { timeNotificationScreenUnlockBroadcastReceiverHandler.onScreenUnlock(any(), any()) }
    }

    @Test
    fun testOnScreenUnlockNotifySubscribers() {

        stubNotificationTimeShow()

        val time: Instant = Instant.now()
        val hour = time.atZone(ZoneId.systemDefault()).hour
        val minute = time.atZone(ZoneId.systemDefault()).minute
        stubNotificationTimeCreate(hour, minute)

        stubTimeNotificationScreenUnlockBroadcastReceiverHandler()

        val screenUnlockBroadcastReceiverContract = mockk<ScreenUnlockBroadcastReceiverContract>()
        every { screenUnlockBroadcastReceiverContract.onScreenUnlock() } returns Unit

        timeNotificationService.addUnlockReceiverSubscriber(screenUnlockBroadcastReceiverContract)
        timeNotificationService.onScreenUnlock()

        verify { screenUnlockBroadcastReceiverContract.onScreenUnlock() }
    }

    private fun getTemporaryAccessLogListElement() = TemporaryAccessLogListElement(
        timeOn = null,
        timeOff = null,
        totalTime = null
    )

    private fun stubNotificationTimeShow() {
        every { notificationTimeShow.showNotification(any(), any(), any()) } returns Unit
    }

    private fun stubNotificationTimeCreate(hours: Int, minutes: Int) {
        every {
            notificationTimeCreate.createNotification(any(), hours, minutes)
        } returns Notification()
    }

    private fun stubTimeNotificationScreenLockBroadcastReceiverHandler(accessLogListElement: TemporaryAccessLogListElement) {
        every {
            timeNotificationScreenLockBroadcastReceiverHandler.onScreenLock(any(), any())
        } returns accessLogListElement
    }

    private fun stubTimeNotificationScreenUnlockBroadcastReceiverHandler() {
        every {
            timeNotificationScreenUnlockBroadcastReceiverHandler.onScreenUnlock(any(), any())
        } returns Unit
    }
}