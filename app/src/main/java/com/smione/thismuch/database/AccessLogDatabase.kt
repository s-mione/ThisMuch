package com.smione.thismuch.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smione.thismuch.model.repository.accesslog.database.AccessLogDao
import com.smione.thismuch.model.repository.accesslog.database.entity.AccessLogEntity
import com.smione.thismuch.model.repository.statistics.database.StatisticsDao

@Database(entities = [AccessLogEntity::class], version = 2)
abstract class RoomAccessLogDatabase : RoomDatabase() {
    abstract fun roomAccessLogDao(): AccessLogDao
    abstract fun roomStatisticDao(): StatisticsDao
}