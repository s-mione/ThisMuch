package com.smione.thismuch.model.repository.accesslog.database

import android.content.Context
import androidx.room.Room

interface AccessLogDatabaseProvider {
    companion object {
        @Volatile
        internal var dbSingleton: AccessLogDatabaseInterface? = null
    }

    fun main(): AccessLogDatabaseInterface
}

class RoomAccessLogDatabaseProvider(val context: Context) : AccessLogDatabaseProvider {
    override fun main(): AccessLogDatabaseInterface {
        return AccessLogDatabaseProvider.dbSingleton ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                this.context.applicationContext,
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