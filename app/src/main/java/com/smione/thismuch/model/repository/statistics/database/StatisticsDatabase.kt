package com.smione.thismuch.model.repository.statistics.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smione.thismuch.model.repository.accesslog.database.entity.AccessLogEntity

@Database(entities = [AccessLogEntity::class], version = 2)
abstract class RoomStatisticsDatabase : RoomDatabase() {
    abstract fun roomStatisticsDao(): StatisticsDatabaseInterface
}