package com.smione.thismuch.model.repository.accesslog.database

import android.content.Context
import androidx.room.Room

interface AccessLogDatabaseProvider {
    companion object {
        @Volatile
        internal var dbSingleton: AccessLogDatabaseInterface? = null
    }

    fun main(applicationContext: Context): AccessLogDatabaseInterface
}

class RoomAccessLogDatabaseProvider() : AccessLogDatabaseProvider {
    override fun main(applicationContext: Context): AccessLogDatabaseInterface {
        return AccessLogDatabaseProvider.dbSingleton ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                applicationContext,
                RoomAccessLogDatabase::class.java,
                "AccessLogRepository"
            )
                .fallbackToDestructiveMigration()
                .build()
            AccessLogDatabaseProvider.dbSingleton = instance.roomAccessLogDao()
            instance.roomAccessLogDao()
        }
    }
}