package com.smione.thismuch.service.timenotification.receiver

import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.model.element.AccessLogListElement
import com.smione.thismuch.service.timenotification.receiver.TimeNotificationScreenLockBroadcastReceiverHandlerInterface.TemporaryAccessLogListElement
import timber.log.Timber

class TimeNotificationReceiverHelper {

    companion object {
        fun saveAccessLogEntityIfFilled(temporaryAccessLogListElement: TemporaryAccessLogListElement,
                                        roomAccessLogRepositoryPresenter: AccessLogRepositoryContract.Presenter,
                                        resetAlsoIfNotSaved: Boolean = false) {
            val accessLogListElement =
                convertTemporaryAccessLogListElementToObject(
                    temporaryAccessLogListElement
                )
            accessLogListElement?.run {
                Timber.Forest.d(
                    "TimeNotificationService saveAccessLogEntityIfFilled: saving temporary element $accessLogListElement"
                )
                roomAccessLogRepositoryPresenter.saveAccessLogElement(this)
                resetTemporaryAccessLogListElementValues(temporaryAccessLogListElement)
            }
                ?: run {
                    Timber.Forest.d(
                        "TimeNotificationService saveAccessLogEntityIfFilled: not saving temporary element because it is null"
                    )
                    if (resetAlsoIfNotSaved) {
                        resetTemporaryAccessLogListElementValues(temporaryAccessLogListElement)
                    }
                }
        }

        private fun convertTemporaryAccessLogListElementToObject(accessLogListElement: TemporaryAccessLogListElement): AccessLogListElement? {
            if (!checkIfTemporaryAccessLogEntityIsToSave(accessLogListElement)) {
                Timber.Forest.d("TimeNotificationService convertTemporaryAccessLogListElementToObject: one of the temporary element is null timeOn: $accessLogListElement.timeOn timeOff: $accessLogListElement.timeOff totalTime: $accessLogListElement.totalTime")
                return null
            }
            return AccessLogListElement(
                0,
                accessLogListElement.timeOn!!,
                accessLogListElement.timeOff!!,
                accessLogListElement.totalTime!!
            )
        }

        private fun checkIfTemporaryAccessLogEntityIsToSave(accessLogListElement: TemporaryAccessLogListElement): Boolean {
            Timber.Forest.d(
                "TimeNotificationService checkIfTemporaryAccessLogEntityIsToSave: temporary element timeOn: $accessLogListElement.timeOn timeOff: $accessLogListElement.timeOff totalTime: $accessLogListElement.totalTime\""
            )
            return accessLogListElement.timeOn != null && accessLogListElement.timeOff != null && accessLogListElement.totalTime != null
        }

        private fun resetTemporaryAccessLogListElementValues(accessLogListElement: TemporaryAccessLogListElement) {
            Timber.Forest.d(
                "TimeNotificationService saveAccessLogEntityIfFilled: resetting temporary element"
            )
            accessLogListElement.timeOn = null
            accessLogListElement.timeOff = null
            accessLogListElement.totalTime = null
        }
    }

}