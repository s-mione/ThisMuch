package com.smione.thismuch.repositorycontract

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement
import java.time.Instant

interface AccessLogRepositoryContract {

    fun getHeaders(): List<String>
    fun getAccessList(): List<AccessLogListElement>

    fun saveLog(element: AccessLogEntity)

}

@Entity
data class AccessLogEntity(
    @ColumnInfo(name = "time_on") val timeOn: Instant?,
    @ColumnInfo(name = "time_off") val timeOff: Instant?,
    @ColumnInfo(name = "total_time") val totalTime: Instant?,
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)