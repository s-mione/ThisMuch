package com.smione.thismuch.service.timenotification.receiver

import com.smione.thismuch.contract.AccessLogRepositoryContract
import java.time.Duration
import java.time.Instant

interface TimeNotificationScreenLockBroadcastReceiverHandlerInterface {
    data class TemporaryAccessLogListElement(
        var timeOn: Instant?,
        var timeOff: Instant?,
        var totalTime: Duration?
    )

    fun onScreenLock(accessLogListElement: TemporaryAccessLogListElement,
                     roomAccessLogRepositoryPresenter: AccessLogRepositoryContract.Presenter)

}