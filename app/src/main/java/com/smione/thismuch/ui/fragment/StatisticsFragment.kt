package com.smione.thismuch.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smione.thismuch.contract.StatisticsRepositoryContract
import com.smione.thismuch.databinding.FragmentStatisticsBinding
import com.smione.thismuch.model.converter.InstantDurationStringConverter
import com.smione.thismuch.model.repository.statistics.StatisticsRepositoryInterface
import com.smione.thismuch.presenter.RuntimeDispatcherProvider
import com.smione.thismuch.presenter.RuntimeScopeProvider
import com.smione.thismuch.presenter.StatisticsRepositoryPresenter
import timber.log.Timber
import java.time.Duration

class StatisticsFragment(private val statisticsRepository: StatisticsRepositoryInterface) :
    Fragment(), StatisticsRepositoryContract.View {

    private lateinit var context: Context
    private lateinit var binding: FragmentStatisticsBinding

    private lateinit var presenter: StatisticsRepositoryContract.Presenter

    companion object {
        const val TAG = "StatisticsFragment"
        fun newInstance(statisticsRepository: StatisticsRepositoryInterface): StatisticsFragment =
            StatisticsFragment(statisticsRepository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = StatisticsRepositoryPresenter(
            RuntimeScopeProvider(),
            RuntimeDispatcherProvider(),
            statisticsRepository
        )
        presenter.bindView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statisticsRepository.initDatabase(this.context.applicationContext)
    }

    override fun onResume() {
        super.onResume()
        presenter.getAvgTimeUp()
    }

    override fun onGetAvgTimeUp(avgTimeUp: Duration) {
        Timber.v("$TAG onGetAvgTimeUp $avgTimeUp")
        binding.tvAvgTimeUp.text =
            InstantDurationStringConverter.fromDurationToFormattedString(avgTimeUp)
    }

}