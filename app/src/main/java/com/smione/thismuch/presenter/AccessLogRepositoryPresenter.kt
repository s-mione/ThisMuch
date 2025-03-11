package com.smione.thismuch.presenter

import android.util.Log
import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.model.converter.AccessLogListElementAccessLogEntityConverter
import com.smione.thismuch.model.repository.AccessLogRepositoryInterface
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class AccessLogRepositoryPresenter(
    private val dispatcherProvider: DispatcherProvider,
    private val accessLogRepository: AccessLogRepositoryInterface
) : CoroutineScope, AccessLogRepositoryContract.Presenter {

    private var view: AccessLogRepositoryContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + dispatcherProvider.io()

    override fun bindView(view: AccessLogRepositoryContract.View) {
        this.view = view
    }

    override fun getHeaders(): List<String> {
        return accessLogRepository.getHeaders()
    }

    override fun getAccessLogList() {
        launch {
            val accessLogList = accessLogRepository.getAccessLogListSortedByTimeDesc().map {
                AccessLogListElementAccessLogEntityConverter.fromAccessEntityToAccessListElement(it)
            }
            view?.onGetAccessLogList(accessLogList)
            Log.v("RoomAccessLogRepository", "getAccessList: $accessLogList")
        }
    }

    override fun saveAccessLogElement(element: AccessLogListElement) {
        Log.v("RoomAccessLogRepository", "saving element: $element")
        launch {
            val accessEntity = AccessLogListElementAccessLogEntityConverter
                .fromAccessListElementToAccessEntity(element)
            accessLogRepository.saveAccessLogEntity(accessEntity)
        }
    }

    override fun deleteAll() {
        Log.v("RoomAccessLogRepository", "delete all")
        launch {
            accessLogRepository.deleteAll()
            view?.onDeleteAll()
        }
    }
}