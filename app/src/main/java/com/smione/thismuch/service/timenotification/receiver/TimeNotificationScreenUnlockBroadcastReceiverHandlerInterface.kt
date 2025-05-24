package com.smione.thismuch.service.timenotification.receiver

import com.smione.thismuch.service.timenotification.receiver.TimeNotificationScreenLockBroadcastReceiverHandlerInterface.TemporaryAccessLogListElement

interface TimeNotificationScreenUnlockBroadcastReceiverHandlerInterface {
    fun onScreenUnlock(temporaryAccessLogListElement: TemporaryAccessLogListElement,
                       roomAccessLogRepositoryPresenter: com.smione.thismuch.contract.AccessLogRepositoryContract.Presenter)
}