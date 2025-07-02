package com.smione.thismuch.database

import android.content.Context
import androidx.room.Room

interface AccessLogDatabaseProvider {
    companion object {
        @Volatile
        internal var dbSingleton: RoomAccessLogDatabase? = null
    }

    fun main(applicationContext: Context): RoomAccessLogDatabase
}

class RoomAccessLogDatabaseProvider() : AccessLogDatabaseProvider {
    override fun main(applicationContext: Context): RoomAccessLogDatabase {
        return AccessLogDatabaseProvider.dbSingleton ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                applicationContext,
                RoomAccessLogDatabase::class.java,
                "AccessLogRepository"
            )
                .fallbackToDestructiveMigration()
                .build()
            instance
        }
    }
}