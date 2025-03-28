package com.smione.thismuch.presenter

import android.util.Log
import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.model.converter.AccessLogListElementAccessLogEntityConverter
import com.smione.thismuch.model.element.AccessLogListElement
import com.smione.thismuch.model.repository.AccessLogRepositoryInterface
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

    override fun getAccessLogListIndexedByTimeDesc() {
        launch {
            val accessLogEntityList = accessLogRepository.getAccessLogListSortedByTimeDesc()
            var index = accessLogEntityList.size
            val accessLogElementList = accessLogEntityList.map {
                AccessLogListElementAccessLogEntityConverter.fromAccessEntityToAccessListElementWithIndex(
                    index--, it
                )
            }
            view?.onGetAccessLogList(accessLogElementList)
            Log.v("RoomAccessLogRepository", "getAccessList: $accessLogElementList")
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