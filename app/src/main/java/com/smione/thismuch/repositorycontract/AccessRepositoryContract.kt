package com.smione.thismuch.repositorycontract

import com.smione.thismuch.ui.fragment.recyclerview.AccessListElement

interface AccessRepositoryContract {

    fun getHeaders(): List<String>
    fun getAccessList(): List<AccessListElement>

}