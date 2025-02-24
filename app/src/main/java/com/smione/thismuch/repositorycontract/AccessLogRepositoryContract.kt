package com.smione.thismuch.repositorycontract

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement

interface AccessLogRepositoryContract {

    fun getHeaders(): List<String>

    fun getAccessList(): List<AccessLogListElement>

    fun saveLog(element: AccessLogEntity)

}

@Entity
data class AccessLogEntity(
    @ColumnInfo(name = "time_on") var timeOn: String?,
    @ColumnInfo(name = "time_off") var timeOff: String?,
    @ColumnInfo(name = "total_time") var totalTime: String?,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)