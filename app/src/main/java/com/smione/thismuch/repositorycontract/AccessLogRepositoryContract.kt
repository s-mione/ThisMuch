package com.smione.thismuch.repositorycontract

import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement

interface AccessLogRepositoryContract {

    fun getHeaders(): List<String>
    fun getAccessList(): List<AccessLogListElement>

    fun saveLog(element: AccessLogListElement)

}