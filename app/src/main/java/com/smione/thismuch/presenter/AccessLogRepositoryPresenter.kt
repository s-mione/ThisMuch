package com.smione.thismuch.presenter

import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.model.converter.AccessLogListElementAccessLogEntityConverter
import com.smione.thismuch.model.element.AccessLogListElement
import com.smione.thismuch.model.repository.accesslog.AccessLogRepositoryInterface
import com.smione.thismuch.model.repository.accesslog.database.entity.AccessLogEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

class AccessLogRepositoryPresenter(
    private val scopeProvider: ScopeProvider,
    private val dispatcherProvider: DispatcherProvider,
    private val accessLogRepository: AccessLogRepositoryInterface
) : CoroutineScope, AccessLogRepositoryContract.Presenter {

    private var view: AccessLogRepositoryContract.View? = null

    override val coroutineContext: CoroutineContext
        get() = SupervisorJob() + dispatcherProvider.io()

    private val coroutineScope: CoroutineScope
        get() = scopeProvider.supervisorJobProvider(dispatcherProvider.io())

    override fun bindView(view: AccessLogRepositoryContract.View) {
        this.view = view
    }

    override fun getHeaders(): List<String> {
        return accessLogRepository.getHeaders()
    }

    override fun getAccessLogListIndexedByTimeDesc() {
        coroutineScope.launch {
            val accessLogEntityList = accessLogRepository.getAccessLogListSortedByTimeDesc()
            val accessLogElementList = convertAccessLogEntityListToAccessLogElementList(
                accessLogEntityList
            )
            view?.onGetAccessLogList(accessLogElementList)
            Timber.v("RoomAccessLogRepository getAccessList: $accessLogElementList")
        }
    }

    private fun convertAccessLogEntityListToAccessLogElementList(
        accessLogEntityList: List<AccessLogEntity>
    ): List<AccessLogListElement> {
        var index = accessLogEntityList.size
        val accessLogElementList = accessLogEntityList.map {
            AccessLogListElementAccessLogEntityConverter.fromAccessEntityToAccessListElementWithIndex(
                index--, it
            )
        }
        return accessLogElementList
    }

    override fun saveAccessLogElement(element: AccessLogListElement) {
        Timber.v("RoomAccessLogRepository saving element: $element")
        coroutineScope.launch {
            val accessEntity = AccessLogListElementAccessLogEntityConverter
                .fromAccessListElementToAccessEntity(element)
            accessLogRepository.saveAccessLogEntity(accessEntity)
        }
    }

    override fun deleteAll() {
        Timber.v("RoomAccessLogRepository delete all")
        coroutineScope.launch {
            accessLogRepository.deleteAll()
            view?.onDeleteAll()
        }
    }
}