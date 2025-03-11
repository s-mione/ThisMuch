package com.smione.thismuch.contract

import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement

interface AccessLogRepositoryContract {
    interface View {
        fun onGetAccessLogList(accessLogList: List<AccessLogListElement>)
    }

    interface Presenter {
        fun bindView(view: View)

        fun getHeaders(): List<String>
        fun getAccessLogList()
        fun saveAccessLogElement(element: AccessLogListElement)
    }
}