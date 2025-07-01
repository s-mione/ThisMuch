package com.smione.thismuch.model.repository.statistics

import android.content.Context
import java.time.Duration

interface StatisticsRepositoryInterface {
    fun initDatabase(applicationContext: Context)

    fun getAvgTimeUp(): Duration
}