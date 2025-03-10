package com.smione.thismuch.model.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

interface AccessLogRepositoryInterface {

    fun getHeaders(): List<String>
    suspend fun getAccessLogList(): List<AccessLogEntity>
    suspend fun saveAccessLogElement(element: AccessLogEntity)

}

@Entity
data class AccessLogEntity(
    @ColumnInfo(name = "time_on") var timeOn: String?,
    @ColumnInfo(name = "time_off") var timeOff: String?,
    @ColumnInfo(name = "total_time") var totalTime: String?,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)