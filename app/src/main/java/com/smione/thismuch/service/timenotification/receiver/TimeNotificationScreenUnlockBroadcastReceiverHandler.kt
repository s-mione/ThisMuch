package com.smione.thismuch.service.timenotification.receiver

import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.service.timenotification.receiver.TimeNotificationScreenLockBroadcastReceiverHandlerInterface.TemporaryAccessLogListElement
import timber.log.Timber
import java.time.Instant

class TimeNotificationScreenUnlockBroadcastReceiverHandler :
    TimeNotificationScreenUnlockBroadcastReceiverHandlerInterface {

    override fun onScreenUnlock(temporaryAccessLogListElement: TemporaryAccessLogListElement,
                                roomAccessLogRepositoryPresenter: AccessLogRepositoryContract.Presenter) {
        Timber.Forest.v("TimeNotificationService onScreenUnlock")

        TimeNotificationReceiverTestHelper.saveAccessLogEntityIfFilled(
            temporaryAccessLogListElement,
            roomAccessLogRepositoryPresenter,
            true
        )
        setTemporaryAccessLogListElementValuesWhenUnlock(temporaryAccessLogListElement)
    }

    private fun setTemporaryAccessLogListElementValuesWhenUnlock(temporaryAccessLogListElement: TemporaryAccessLogListElement) {
        temporaryAccessLogListElement.timeOn = Instant.now()
    }

}