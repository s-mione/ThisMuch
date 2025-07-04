package com.smione.thismuch.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.databinding.FragmentAccessListBinding
import com.smione.thismuch.model.repository.accesslog.AccessLogRepositoryInterface
import com.smione.thismuch.presenter.AccessLogRepositoryPresenter
import com.smione.thismuch.presenter.RuntimeDispatcherProvider
import com.smione.thismuch.presenter.RuntimeScopeProvider
import com.smione.thismuch.receivercontract.ScreenUnlockBroadcastReceiverContract
import com.smione.thismuch.service.timenotification.TimeNotificationService
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListRecyclerViewAdapter
import com.smione.thismuch.utils.init.MainActivityUtils
import timber.log.Timber

class AccessLogListFragment(private val accessLogRepository: AccessLogRepositoryInterface,
                            private val timeNotificationService: TimeNotificationService) :
    Fragment(), ScreenUnlockBroadcastReceiverContract, AccessLogRepositoryContract.View {

    private lateinit var context: Context
    private lateinit var binding: FragmentAccessListBinding

    private lateinit var presenter: AccessLogRepositoryContract.Presenter

    companion object {
        const val TAG = "AccessListFragment"
        fun newInstance(accessRepository: AccessLogRepositoryInterface,
                        timeNotificationService: TimeNotificationService): AccessLogListFragment =
            AccessLogListFragment(accessRepository, timeNotificationService)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.context = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = AccessLogRepositoryPresenter(
            RuntimeScopeProvider(),
            RuntimeDispatcherProvider(),
            accessLogRepository
        )
        presenter.bindView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAccessListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivityUtils.askToIgnoreBatteryOptimization(requireContext())
        MainActivityUtils.askForNotificationPermission(requireContext())
        MainActivityUtils.startTimeNotificationServiceIfNotRunning(
            context,
            timeNotificationService,
            this
        )

        val headers = presenter.getHeaders()
        presenter.getAccessLogList()
        accessLogRepository.initDatabase(this.context.applicationContext)
        binding.rvList.adapter = AccessLogListRecyclerViewAdapter(headers, emptyList())

        binding.fabDeleteAll.setOnClickListener { deleteAll() }
    }

    private fun deleteAll() {
        presenter.deleteAll()
    }

    override fun onScreenUnlock() {
        Timber.v("$TAG onScreenUnlock")
        presenter.getAccessLogList()
    }

    override fun onGetAccessLogList(accessLogList: List<AccessLogListElement>) {
        (binding.rvList.adapter as AccessLogListRecyclerViewAdapter).updateValues(
            accessLogList,
            binding.rvList
        )
    }

    override fun onDeleteAll() {
        (binding.rvList.adapter as AccessLogListRecyclerViewAdapter).deleteAll(
            binding.rvList
        )
    }
}