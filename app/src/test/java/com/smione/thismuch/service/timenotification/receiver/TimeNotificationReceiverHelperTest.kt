package com.smione.thismuch.service.timenotification.receiver

import com.smione.thismuch.InstantCommonTestHelper
import com.smione.thismuch.contract.AccessLogRepositoryContract
import io.mockk.called
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class TimeNotificationReceiverHelperTest {

    @Test
    fun testSaveAccessLogEntityIfFilled() {
        val temporaryAccessLogListElement =
            InstantCommonTestHelper.getTemporaryAccessLogListElement()
        val accessLogListElement = InstantCommonTestHelper.getAccessLogListElement()

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
        val temporaryAccessLogListElement =
            InstantCommonTestHelper.getTemporaryAccessLogListElement()
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
        val temporaryAccessLogListElement =
            InstantCommonTestHelper.getTemporaryAccessLogListElement()
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
        val temporaryAccessLogListElement =
            InstantCommonTestHelper.getTemporaryAccessLogListElement()
        temporaryAccessLogListElement.totalTime = null
        val roomAccessLogRepositoryPresenter = mockk<AccessLogRepositoryContract.Presenter>()


        TimeNotificationReceiverHelper.saveAccessLogEntityIfFilled(
            temporaryAccessLogListElement,
            roomAccessLogRepositoryPresenter
        )

        verify { roomAccessLogRepositoryPresenter wasNot called }
    }
}