package com.smione.thismuch.model.repository.statistics.database

import android.content.Context
import androidx.room.Room

interface StatisticsDatabaseProvider {
    companion object {
        @Volatile
        internal var dbSingleton: StatisticsDatabaseInterface? = null
    }

    fun main(applicationContext: Context): StatisticsDatabaseInterface
}

class RoomStatisticsDatabaseProvider() : StatisticsDatabaseProvider {
    override fun main(applicationContext: Context): StatisticsDatabaseInterface {
        return StatisticsDatabaseProvider.dbSingleton ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                applicationContext,
                RoomStatisticsDatabase::class.java,
                "StatisticsRepository"
            )
                .fallbackToDestructiveMigration()
                .build()
            StatisticsDatabaseProvider.dbSingleton = instance.roomStatisticsDao()
            instance.roomStatisticsDao()
        }
    }
}