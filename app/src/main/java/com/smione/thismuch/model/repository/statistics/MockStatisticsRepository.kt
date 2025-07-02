package com.smione.thismuch.model.repository.statistics

import android.content.Context
import com.smione.thismuch.model.repository.accesslog.database.entity.AccessLogEntity
import java.time.Duration

class MockStatisticsRepository() : StatisticsRepositoryInterface {

    val accessList: MutableList<AccessLogEntity> = mutableListOf()

    override fun initDatabase(applicationContext: Context) {
        accessList.addAll(
            0, listOf(
                AccessLogEntity(
                    "2023-01-01T10:00:00Z",
                    "2023-01-01T12:00:00Z",
                    "PT2H"
                ),
                AccessLogEntity(
                    "2023-01-02T10:00:00Z",
                    "2023-01-02T12:00:00Z",
                    "PT2H"
                ),
                AccessLogEntity(
                    "2023-01-03T10:00:00Z",
                    "2023-01-03T12:00:00Z",
                    "PT2H"
                ),
                AccessLogEntity(
                    "2023-01-04T10:00:00Z",
                    "2023-01-04T12:00:00Z",
                    "PT2H"
                ),
                AccessLogEntity(
                    "2023-01-05T10:00:00Z",
                    "2023-01-05T12:00:00Z",
                    "PT2H"
                ),
                AccessLogEntity(
                    "2023-01-06T10:00:00Z",
                    "2023-01-06T12:00:00Z",
                    "PT2H"
                ),
                AccessLogEntity(
                    "2023-01-07T10:00:00Z",
                    "2023-01-07T12:00:00Z",
                    "PT2H"
                ),
                AccessLogEntity(
                    "2023-01-08T10:00:00Z",
                    "2023-01-08T12:00:00Z",
                    "PT2H"
                ),
                AccessLogEntity(
                    "2023-01-09T10:00:00Z",
                    "2023-01-09T12:00:00Z",
                    "PT2H"
                ),
                AccessLogEntity(
                    "2023-01-10T10:00:00Z",
                    "2023-01-10T12:30:00Z",
                    "PT2H30M"
                ),
                AccessLogEntity(
                    "2023-01-10T11:00:00Z",
                    "2023-01-10T12:00:00Z",
                    "PT1H"
                ),
                AccessLogEntity(
                    "2023-01-10T10:00:00Z",
                    "2023-01-10T12:00:00Z",
                    "PT2H"
                )
            )
        )
    }

    override fun getAvgTimeUp(): Duration {
        val totalAcc = accessList.reduce { acc, accessLogEntity ->
            val totalTime = accessLogEntity.totalTime

            acc.totalTime = Duration.parse(acc.totalTime).plus(Duration.parse(totalTime)).toString()
            acc
        }
        
        return Duration.parse(totalAcc.totalTime).dividedBy(accessList.size.toLong())
    }
}