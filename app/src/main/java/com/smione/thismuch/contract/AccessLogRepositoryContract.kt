package com.smione.thismuch.contract

import com.smione.thismuch.model.element.AccessLogListElement
import com.smione.thismuch.model.repository.accesslog.AccessLogRepositoryInterface

interface AccessLogRepositoryContract {
    interface View {
        fun onGetAccessLogList(accessLogList: List<AccessLogListElement>)
        fun onDeleteAll()
    }

    interface Presenter {
        val getAccessLogRepository: AccessLogRepositoryInterface
        fun bindView(view: View)

        fun getHeaders(): List<String>
        fun getAccessLogList()
        fun saveAccessLogElement(element: AccessLogListElement)
        fun deleteAll()
    }
}