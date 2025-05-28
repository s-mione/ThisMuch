package com.smione.thismuch.service.timenotification.receiver

import com.smione.thismuch.InstantCommonTestHelper
import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.model.element.AccessLogListElement
import io.mockk.called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class TimeNotificationReceiverHelperTest {

    @Test
    fun testSaveAccessLogEntityIfFilled() {
        val temporaryAccessLogListElement = getTemporaryAccessLogListElement()
        val accessLogListElement = getAccessLogListElement()
        val roomAccessLogRepositoryPresenter = mockk<AccessLogRepositoryContract.Presenter>()

        every { roomAccessLogRepositoryPresenter.saveAccessLogElement(any()) } returns Unit

        TimeNotificationReceiverHelper.saveAccessLogEntityIfFilled(
            temporaryAccessLogListElement,
            roomAccessLogRepositoryPresenter
        )

        verify { roomAccessLogRepositoryPresenter.saveAccessLogElement(accessLogListElement) }
    }

    @Test
    fun testSaveAccessLogEntityIfFilledWithTimeOnNull() {
        val temporaryAccessLogListElement = getTemporaryAccessLogListElement()
        temporaryAccessLogListElement.timeOn = null
        val roomAccessLogRepositoryPresenter = mockk<AccessLogRepositoryContract.Presenter>()


        TimeNotificationReceiverHelper.saveAccessLogEntityIfFilled(
            temporaryAccessLogListElement,
            roomAccessLogRepositoryPresenter
        )

        verify { roomAccessLogRepositoryPresenter wasNot called }
    }

    @Test
    fun testSaveAccessLogEntityIfFilledWithTimeOffNull() {
        val temporaryAccessLogListElement = getTemporaryAccessLogListElement()
        temporaryAccessLogListElement.timeOff = null
        val roomAccessLogRepositoryPresenter = mockk<AccessLogRepositoryContract.Presenter>()


        TimeNotificationReceiverHelper.saveAccessLogEntityIfFilled(
            temporaryAccessLogListElement,
            roomAccessLogRepositoryPresenter
        )

        verify { roomAccessLogRepositoryPresenter wasNot called }
    }

    @Test
    fun testSaveAccessLogEntityIfFilledWithTotalTimeNull() {
        val temporaryAccessLogListElement = getTemporaryAccessLogListElement()
        temporaryAccessLogListElement.totalTime = null
        val roomAccessLogRepositoryPresenter = mockk<AccessLogRepositoryContract.Presenter>()


        TimeNotificationReceiverHelper.saveAccessLogEntityIfFilled(
            temporaryAccessLogListElement,
            roomAccessLogRepositoryPresenter
        )

        verify { roomAccessLogRepositoryPresenter wasNot called }
    }

    private fun getTemporaryAccessLogListElement() =
        TimeNotificationScreenLockBroadcastReceiverHandlerInterface.TemporaryAccessLogListElement(
            InstantCommonTestHelper.INSTANT_TIME_ON,
            InstantCommonTestHelper.INSTANT_TIME_OFF,
            InstantCommonTestHelper.TOTAL_TIME
        )

    private fun getAccessLogListElement() =
        AccessLogListElement(
            0,
            InstantCommonTestHelper.INSTANT_TIME_ON,
            InstantCommonTestHelper.INSTANT_TIME_OFF,
            InstantCommonTestHelper.TOTAL_TIME
        )
}