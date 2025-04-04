package com.smione.thismuch.ui.accessLogList

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.model.element.AccessLogListElement
import com.smione.thismuch.model.repository.AccessLogRepositoryInterface
import com.smione.thismuch.presenter.AccessLogRepositoryPresenter
import com.smione.thismuch.presenter.RuntimeDispatcherProvider
import com.smione.thismuch.receivercontract.ScreenUnlockBroadcastReceiverContract
import com.smione.thismuch.service.TimeNotificationService
import com.smione.thismuch.utils.init.MainActivityUtils

class AccessLogListViewModel(private val accessLogRepository: AccessLogRepositoryInterface,
                             private val timeNotificationService: TimeNotificationService) :
    Fragment(), ScreenUnlockBroadcastReceiverContract, AccessLogRepositoryContract.View {

    private lateinit var context: Context

    private lateinit var presenter: AccessLogRepositoryContract.Presenter

    private var uiState by mutableStateOf(AccessLogListUiState())

    companion object {
        const val TAG = "AccessListFragment"
        fun newInstance(
            accessRepository: AccessLogRepositoryInterface,
            timeNotificationService: TimeNotificationService
        ): AccessLogListViewModel =
            AccessLogListViewModel(accessRepository, timeNotificationService)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        this.context = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = AccessLogRepositoryPresenter(RuntimeDispatcherProvider(), accessLogRepository)
        presenter.bindView(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AccessLogListScreen(uiState, onDeleteAll = { deleteAll() })
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        MainActivityUtils.askToIgnoreBatteryOptimization(requireContext())
        MainActivityUtils.startTimeNotificationServiceIfNotRunning(
            context,
            timeNotificationService,
            this
        )

        val headers = presenter.getHeaders()
        uiState = uiState.copy(isLoading = true, headers = headers)
        presenter.getAccessLogListIndexedByTimeDesc()
    }

    private fun deleteAll() {
        presenter.deleteAll()
    }

    override fun onScreenUnlock() {
        Log.v(TAG, "onScreenUnlock")
        uiState = uiState.copy(isLoading = true)
        presenter.getAccessLogListIndexedByTimeDesc()
    }

    override fun onGetAccessLogList(accessLogList: List<AccessLogListElement>) {
        uiState = uiState.copy(isLoading = false, accessLogList = accessLogList)
    }

    override fun onDeleteAll() {
        uiState = uiState.copy(accessLogList = emptyList())
    }
}