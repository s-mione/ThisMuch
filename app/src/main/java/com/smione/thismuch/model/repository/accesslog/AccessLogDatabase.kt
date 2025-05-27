package com.smione.thismuch.model.repository.accesslog

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smione.thismuch.model.repository.accesslog.entity.AccessLogEntity

@Database(entities = [AccessLogEntity::class], version = 2)
abstract class RoomAccessLogDatabase : RoomDatabase() {
    abstract fun roomAccessLogDao(): AccessLogDatabaseInterface
}