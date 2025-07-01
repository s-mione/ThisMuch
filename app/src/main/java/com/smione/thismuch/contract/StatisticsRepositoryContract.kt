package com.smione.thismuch.contract

import com.smione.thismuch.model.repository.statistics.StatisticsRepositoryInterface
import java.time.Duration

interface StatisticsRepositoryContract {
    interface View {
        fun onGetAvgTimeUp(avgTimeUp: Duration)
    }

    interface Presenter {
        val getStatisticsRepository: StatisticsRepositoryInterface
        fun bindView(view: View)

        fun getAvgTimeUp()
    }
}