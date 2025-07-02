package com.smione.thismuch.model.repository.accesslog

import android.content.Context
import com.smione.thismuch.database.AccessLogDatabaseProvider
import com.smione.thismuch.model.repository.accesslog.database.AccessLogDao
import com.smione.thismuch.model.repository.accesslog.database.entity.AccessLogEntity
import timber.log.Timber

class RoomAccessLogRepository(val databaseProvider: AccessLogDatabaseProvider) :
    AccessLogRepositoryInterface {

    private var database: AccessLogDao? = null

    override fun initDatabase(applicationContext: Context) {
        Timber.v("RoomAccessLogRepository initDatabase")
        this.database = this.databaseProvider.main(applicationContext).roomAccessLogDao()
    }

    override fun getHeaders(): List<String> {
        return listOf("No.", "Time On", "Time Off", "Total Time")
    }

    override suspend fun saveAccessLogEntity(element: AccessLogEntity) {
        database?.insertAll(element) ?: run {
            logDatabaseIsNotInitialized()
        }
    }

    override suspend fun getAccessLogList(): List<AccessLogEntity> =
        database?.getAll()
            ?: run {
                logDatabaseIsNotInitialized()
                emptyList()
            }

    override suspend fun getAccessLogListSortedByTimeDesc(): List<AccessLogEntity> =
        database?.getAllSortedByTimeDesc()
            ?: run {
                logDatabaseIsNotInitialized()
                emptyList()
            }

    override suspend fun deleteAll() {
        database?.deleteAll() ?: run {
            logDatabaseIsNotInitialized()
        }
    }

    private fun logDatabaseIsNotInitialized() {
        Timber.d("RoomAccessLogRepository database is not initialized")
    }
}