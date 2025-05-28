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

class TimeNotificationScreenUnlockBroadcastReceiverHandlerTest {

    @MockK(relaxed = true)
    lateinit var accessLogRepositoryPresenter: AccessLogRepositoryContract.Presenter

    lateinit var timeNotificationScreenUnlockBroadcastReceiverHandler: TimeNotificationScreenUnlockBroadcastReceiverHandlerInterface

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        timeNotificationScreenUnlockBroadcastReceiverHandler =
            TimeNotificationScreenUnlockBroadcastReceiverHandler()
    }

    @Test
    fun testOnScreenUnlock() {

        val temporaryAccessLogListElement =
            InstantCommonTestHelper.getTemporaryAccessLogListElement()

        mockkStatic(Instant::class)
        every { Instant.now() } returns InstantCommonTestHelper.INSTANT_TIME_ON

        timeNotificationScreenUnlockBroadcastReceiverHandler.onScreenUnlock(
            temporaryAccessLogListElement, accessLogRepositoryPresenter
        )

        assertEquals(
            InstantCommonTestHelper.buildTemporaryAccessLogListElement(
                InstantCommonTestHelper.INSTANT_TIME_ON,
                null,
                null
            ),
            temporaryAccessLogListElement
        )
        verify { accessLogRepositoryPresenter.saveAccessLogElement(InstantCommonTestHelper.getAccessLogListElement()) }
    }

    @Test
    fun testOnScreenUnlockWithTimeOnNull() {

        val temporaryAccessLogListElement =
            InstantCommonTestHelper.getTemporaryAccessLogListElement()
        temporaryAccessLogListElement.timeOn = null

        mockkStatic(Instant::class)
        every { Instant.now() } returns InstantCommonTestHelper.INSTANT_TIME_ON

        timeNotificationScreenUnlockBroadcastReceiverHandler.onScreenUnlock(
            temporaryAccessLogListElement,
            accessLogRepositoryPresenter
        )

        assertEquals(
            InstantCommonTestHelper.buildTemporaryAccessLogListElement(
                InstantCommonTestHelper.INSTANT_TIME_ON, null, null
            ), temporaryAccessLogListElement
        )
    }

}