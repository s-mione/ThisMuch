package com.smione.thismuch.model.repository.statistics

import android.content.Context
import com.smione.thismuch.model.converter.InstantDurationStringConverter
import com.smione.thismuch.model.repository.statistics.database.StatisticsDao
import timber.log.Timber
import java.time.Duration
import java.time.temporal.ChronoUnit

class RoomStatisticsRepository(val databaseProvider: StatisticsDatabaseProvider) :
    StatisticsRepositoryInterface {

    private var database: StatisticsDatabaseInterface? = null

    override fun initDatabase(applicationContext: Context) {
        Timber.v("RoomAccessLogRepository initDatabase")
        this.database = this.databaseProvider.main(applicationContext)
    }

    override fun getAvgTimeUp(): Duration {
        val allTotalTime = database?.getAllTotalTime()
        val allTotalTimeAsDuration = allTotalTime?.map {
            InstantDurationStringConverter.fromStringToDuration(it)
        }
        val totalHours =
            allTotalTimeAsDuration?.sumOf { a -> a.get(ChronoUnit.HOURS) } ?: 0L
        val totalMinuts =
            allTotalTimeAsDuration?.sumOf { a -> a.get(ChronoUnit.MINUTES) } ?: 0L
        val totalSeconds =
            allTotalTimeAsDuration?.sumOf { a -> a.get(ChronoUnit.SECONDS) } ?: 0L
        val avgHours = totalHours /
                (allTotalTimeAsDuration?.size ?: 1).toLong()
        val avgMinuts = totalMinuts /
                (allTotalTimeAsDuration?.size ?: 1).toLong()
        val avgSeconds = totalSeconds /
                (allTotalTimeAsDuration?.size ?: 1).toLong()
        val avgDuration = Duration.ofHours(avgHours).plusMinutes(avgMinuts).plusSeconds(avgSeconds)

        return avgDuration
            ?: run {
                logDatabaseIsNotInitialized()
                return Duration.ZERO
            }
    }

    private fun logDatabaseIsNotInitialized() {
        Timber.d("RoomStatisticsRepository database is not initialized")
    }
}