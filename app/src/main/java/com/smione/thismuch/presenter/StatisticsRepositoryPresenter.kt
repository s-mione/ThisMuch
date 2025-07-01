package com.smione.thismuch.presenter

import com.smione.thismuch.contract.StatisticsRepositoryContract
import com.smione.thismuch.model.repository.statistics.StatisticsRepositoryInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class StatisticsRepositoryPresenter(
    private val scopeProvider: ScopeProvider,
    private val dispatcherProvider: DispatcherProvider,
    private val statisticsRepository: StatisticsRepositoryInterface
) : CoroutineScope, StatisticsRepositoryContract.Presenter {

    private var view: StatisticsRepositoryContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + dispatcherProvider.io()

    private val coroutineScope: CoroutineScope
        get() = scopeProvider.supervisorJobProvider(dispatcherProvider.io())

    override val getStatisticsRepository: StatisticsRepositoryInterface
        get() = statisticsRepository

    override fun bindView(view: StatisticsRepositoryContract.View) {
        this.view = view
    }

    override fun getAvgTimeUp() {
        coroutineScope.launch {
            val avgTimeUp = statisticsRepository.getAvgTimeUp()
            view?.onGetAvgTimeUp(avgTimeUp)
        }
    }
}