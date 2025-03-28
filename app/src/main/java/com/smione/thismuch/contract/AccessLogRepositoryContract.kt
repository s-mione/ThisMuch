package com.smione.thismuch.contract

import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement

interface AccessLogRepositoryContract {
    interface View {
        fun onGetAccessLogList(accessLogList: List<AccessLogListElement>)
        fun onDeleteAll()
    }

    interface Presenter {
        fun bindView(view: View)

        fun getHeaders(): List<String>
        fun getAccessLogListIndexedByTimeDesc()
        fun saveAccessLogElement(element: AccessLogListElement)
        fun deleteAll()
    }
}