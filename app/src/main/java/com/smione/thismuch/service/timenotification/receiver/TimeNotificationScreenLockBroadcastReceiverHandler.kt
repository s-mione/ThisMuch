package com.smione.thismuch.service.timenotification.receiver

import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.service.timenotification.receiver.TimeNotificationScreenLockBroadcastReceiverHandlerInterface.TemporaryAccessLogListElement
import timber.log.Timber
import java.time.Duration
import java.time.Instant

class TimeNotificationScreenLockBroadcastReceiverHandler :
    TimeNotificationScreenLockBroadcastReceiverHandlerInterface {


    override fun onScreenLock(temporaryAccessLogListElement: TemporaryAccessLogListElement,
                              roomAccessLogRepositoryPresenter: AccessLogRepositoryContract.Presenter): TemporaryAccessLogListElement {
        Timber.Forest.v("TimeNotificationService onScreenLock")
        setTemporaryAccessLogListElementValuesWhenLock(temporaryAccessLogListElement)
        TimeNotificationReceiverHelper.saveAccessLogEntityIfFilled(
            temporaryAccessLogListElement,
            roomAccessLogRepositoryPresenter
        )
        return temporaryAccessLogListElement
    }


    private fun setTemporaryAccessLogListElementValuesWhenLock(temporaryAccessLogListElement: TemporaryAccessLogListElement) {
        if (temporaryAccessLogListElement.timeOn != null) {
            temporaryAccessLogListElement.timeOff = Instant.now()

            temporaryAccessLogListElement.totalTime = this.calculateTotalTime(
                temporaryAccessLogListElement.timeOn,
                temporaryAccessLogListElement.timeOff
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