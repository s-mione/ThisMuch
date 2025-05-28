package com.smione.thismuch.service.timenotification.receiver

import com.smione.thismuch.InstantCommonTestHelper
import com.smione.thismuch.contract.AccessLogRepositoryContract
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.time.Instant

class TimeNotificationScreenLockBroadcastReceiverHandlerTest {

    @MockK(relaxed = true)
    lateinit var accessLogRepositoryPresenter: AccessLogRepositoryContract.Presenter

    lateinit var timeNotificationScreenLockBroadcastReceiverHandler: TimeNotificationScreenLockBroadcastReceiverHandlerInterface

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        timeNotificationScreenLockBroadcastReceiverHandler =
            TimeNotificationScreenLockBroadcastReceiverHandler()
    }

    @Test
    fun testOnScreenLock() {

        val temporaryAccessLogListElement =
            InstantCommonTestHelper.getTemporaryAccessLogListElement().also {
                it.timeOff = null
                it.totalTime = null
            }

        mockkStatic(Instant::class)
        every { Instant.now() } returns InstantCommonTestHelper.INSTANT_TIME_OFF

        timeNotificationScreenLockBroadcastReceiverHandler.onScreenLock(
            temporaryAccessLogListElement, accessLogRepositoryPresenter
        )

        assertEquals(
            InstantCommonTestHelper.buildTemporaryAccessLogListElement(null, null, null),
            temporaryAccessLogListElement
        )
        verify { accessLogRepositoryPresenter.saveAccessLogElement(InstantCommonTestHelper.getAccessLogListElement()) }
    }

    @Test
    fun testOnScreenLockWithTimeOnNull() {

        val temporaryAccessLogListElement =
            InstantCommonTestHelper.getTemporaryAccessLogListElement()
        temporaryAccessLogListElement.timeOn = null

        val result = timeNotificationScreenLockBroadcastReceiverHandler.onScreenLock(
            temporaryAccessLogListElement,
            accessLogRepositoryPresenter
        )

        assertEquals(
            InstantCommonTestHelper.buildTemporaryAccessLogListElement(
                null,
                InstantCommonTestHelper.INSTANT_TIME_OFF, InstantCommonTestHelper.TOTAL_TIME
            ), result
        )
    }

}