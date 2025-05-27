package com.smione.thismuch.model.repository.accesslog

import com.smione.thismuch.model.repository.accesslog.database.entity.AccessLogEntity

interface AccessLogRepositoryInterface {
    fun getHeaders(): List<String>
    suspend fun getAccessLogList(): List<AccessLogEntity>
    suspend fun getAccessLogListSortedByTimeDesc(): List<AccessLogEntity>
    suspend fun saveAccessLogEntity(element: AccessLogEntity)

    suspend fun deleteAll()
}