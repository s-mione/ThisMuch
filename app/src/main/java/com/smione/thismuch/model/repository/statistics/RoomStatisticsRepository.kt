package com.smione.thismuch.model.repository.statistics

import android.content.Context
import com.smione.thismuch.database.AccessLogDatabaseProvider
import com.smione.thismuch.model.converter.InstantDurationStringConverter
import com.smione.thismuch.model.repository.statistics.database.StatisticsDao
import timber.log.Timber
import java.time.Duration

class RoomStatisticsRepository(val databaseProvider: AccessLogDatabaseProvider) :
    StatisticsRepositoryInterface {

    private var database: StatisticsDao? = null

    override fun initDatabase(applicationContext: Context) {
        Timber.v("RoomAccessLogRepository initDatabase")
        this.database = this.databaseProvider.main(applicationContext).roomStatisticDao()
    }

    override fun getAvgTimeUp(): Duration {

        val allTotalTime = database?.getAllTotalTime()?.map {
            InstantDurationStringConverter.fromStringToDuration(it)
        }

        if (allTotalTime == null) {
            logDatabaseIsNotInitialized()
            return Duration.ZERO
        }
        if (allTotalTime.isEmpty() == true) {
            return Duration.ZERO
        }

        val allTotalTimeAccumulator = allTotalTime.reduce { acc, duration -> acc.plus(duration) }

        return allTotalTimeAccumulator.dividedBy(allTotalTime.size.toLong())
    }

    private fun logDatabaseIsNotInitialized() {
        Timber.d("RoomStatisticsRepository database is not initialized")
    }
}