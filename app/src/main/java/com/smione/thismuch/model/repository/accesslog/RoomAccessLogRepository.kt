package com.smione.thismuch.model.repository.accesslog

import com.smione.thismuch.model.repository.accesslog.entity.AccessLogEntity

class RoomAccessLogRepository(databaseProvider: AccessLogDatabaseProvider) :
    AccessLogRepositoryInterface {

    val database = databaseProvider.main()

    override fun getHeaders(): List<String> {
        return listOf("No.", "Time On", "Time Off", "Total Time")
    }

    override suspend fun saveAccessLogEntity(element: AccessLogEntity) {
        database.insertAll(element)
    }

    override suspend fun getAccessLogList(): List<AccessLogEntity> {
        return database.getAll()
    }

    override suspend fun getAccessLogListSortedByTimeDesc(): List<AccessLogEntity> {
        return database.getAllSortedByTimeDesc()
    }

    override suspend fun deleteAll() {
        database.deleteAll()
    }
}