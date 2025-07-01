package com.smione.thismuch.model.repository.statistics.database

import androidx.room.Dao
import androidx.room.Query

@Dao
interface StatisticsDatabaseInterface {

    @Query("SELECT total_time FROM AccessLogEntity")
    fun getAllTotalTime(): List<String>
}