package com.smione.thismuch.model.repository

import com.smione.thismuch.converter.AccessLogListElementAccessLogEntityConverter
import com.smione.thismuch.repositorycontract.AccessLogEntity
import com.smione.thismuch.repositorycontract.AccessLogRepositoryContract
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement

class MockAccessLogRepository() : AccessLogRepositoryContract {

    companion object {

        val accessList = mutableListOf<AccessLogEntity>(
            AccessLogEntity(
                "2023-01-01T10:00:00Z",
                "2023-01-01T12:00:00Z",
                "2023-01-01T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-02T10:00:00Z",
                "2023-01-02T12:00:00Z",
                "2023-01-02T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-03T10:00:00Z",
                "2023-01-03T12:00:00Z",
                "2023-01-03T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-04T10:00:00Z",
                "2023-01-04T12:00:00Z",
                "2023-01-04T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-05T10:00:00Z",
                "2023-01-05T12:00:00Z",
                "2023-01-05T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-06T10:00:00Z",
                "2023-01-06T12:00:00Z",
                "2023-01-06T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-07T10:00:00Z",
                "2023-01-07T12:00:00Z",
                "2023-01-07T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-08T10:00:00Z",
                "2023-01-08T12:00:00Z",
                "2023-01-08T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-09T10:00:00Z",
                "2023-01-09T12:00:00Z",
                "2023-01-09T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-10T10:00:00Z",
                "2023-01-10T12:00:00Z",
                "2023-01-10T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-10T10:00:00Z",
                "2023-01-10T12:00:00Z",
                "2023-01-10T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-10T10:00:00Z",
                "2023-01-10T12:00:00Z",
                "2023-01-10T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-10T10:00:00Z",
                "2023-01-10T12:00:00Z",
                "2023-01-10T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-10T10:00:00Z",
                "2023-01-10T12:00:00Z",
                "2023-01-10T02:00:00Z"
            ),
            AccessLogEntity(
                "2023-01-10T10:00:00Z",
                "2023-01-10T12:00:00Z",
                "2023-01-10T02:00:00Z"
            )
        )
    }

    override fun getHeaders(): List<String> {
        return listOf("No.", "Time On", "Time Off", "Total Time")
    }

    override fun getAccessList(): List<AccessLogListElement> {
        return accessList.map {
            AccessLogListElementAccessLogEntityConverter.fromAccessEntityToAccessListElement(it)
        }
    }

    override fun saveLog(element: AccessLogEntity) {
        accessList.add(element)
    }

}