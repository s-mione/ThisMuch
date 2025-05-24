package com.smione.thismuch.service.timenotification.receiver

import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.service.timenotification.receiver.TimeNotificationScreenLockBroadcastReceiverHandlerInterface.TemporaryAccessLogListElement
import timber.log.Timber
import java.time.Duration
import java.time.Instant

class TimeNotificationScreenLockBroadcastReceiverHandler :
    TimeNotificationScreenLockBroadcastReceiverHandlerInterface {


    override fun onScreenLock(accessLogListElement: TemporaryAccessLogListElement,
                              roomAccessLogRepositoryPresenter: AccessLogRepositoryContract.Presenter): TemporaryAccessLogListElement {
        Timber.Forest.v("TimeNotificationService onScreenLock")
        setTemporaryAccessLogListElementValuesWhenLock(accessLogListElement)
        TimeNotificationReceiverTestHelper.saveAccessLogEntityIfFilled(
            accessLogListElement,
            roomAccessLogRepositoryPresenter
        )
        return accessLogListElement
    }


    private fun setTemporaryAccessLogListElementValuesWhenLock(accessLogListElement: TemporaryAccessLogListElement) {
        if (accessLogListElement.timeOn != null) {
            accessLogListElement.timeOff = Instant.now()

            accessLogListElement.totalTime = this.calculateTotalTime(
                accessLogListElement.timeOn,
                accessLogListElement.timeOff
            )
        }
    }

    private fun calculateTotalTime(timeOn: Instant?, timeOff: Instant?): Duration {
        val duration = Duration.between(timeOn, timeOff)
        Timber.Forest.v(
            "TimeNotificationService calculateTotalTime: timeOn $timeOn, timeOff $timeOff, duration $duration"
        )
        return duration
    }

}