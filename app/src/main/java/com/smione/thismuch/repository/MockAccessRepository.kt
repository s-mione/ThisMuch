package com.smione.thismuch.repository

import com.smione.thismuch.repositorycontract.AccessRepositoryContract
import com.smione.thismuch.ui.fragment.recyclerview.AccessListElement
import java.time.Instant

class MockAccessRepository() : AccessRepositoryContract {

    override fun getHeaders(): List<String> {
        return listOf("No.", "Time On", "Time Off", "Total Time")
    }

    override fun getAccessList(): List<AccessListElement> {
        val accessList = mutableListOf<AccessListElement>()
        accessList.add(
            AccessListElement(
                1,
                Instant.parse("2023-01-01T10:00:00Z"),
                Instant.parse("2023-01-01T12:00:00Z"),
                Instant.parse("2023-01-01T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                2,
                Instant.parse("2023-01-02T10:00:00Z"),
                Instant.parse("2023-01-02T12:00:00Z"),
                Instant.parse("2023-01-02T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                3,
                Instant.parse("2023-01-03T10:00:00Z"),
                Instant.parse("2023-01-03T12:00:00Z"),
                Instant.parse("2023-01-03T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                4,
                Instant.parse("2023-01-04T10:00:00Z"),
                Instant.parse("2023-01-04T12:00:00Z"),
                Instant.parse("2023-01-04T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                5,
                Instant.parse("2023-01-05T10:00:00Z"),
                Instant.parse("2023-01-05T12:00:00Z"),
                Instant.parse("2023-01-05T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                6,
                Instant.parse("2023-01-06T10:00:00Z"),
                Instant.parse("2023-01-06T12:00:00Z"),
                Instant.parse("2023-01-06T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                7,
                Instant.parse("2023-01-07T10:00:00Z"),
                Instant.parse("2023-01-07T12:00:00Z"),
                Instant.parse("2023-01-07T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                8,
                Instant.parse("2023-01-08T10:00:00Z"),
                Instant.parse("2023-01-08T12:00:00Z"),
                Instant.parse("2023-01-08T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                9,
                Instant.parse("2023-01-09T10:00:00Z"),
                Instant.parse("2023-01-09T12:00:00Z"),
                Instant.parse("2023-01-09T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                10,
                Instant.parse("2023-01-10T10:00:00Z"),
                Instant.parse("2023-01-10T12:00:00Z"),
                Instant.parse("2023-01-10T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                11,
                Instant.parse("2023-01-10T10:00:00Z"),
                Instant.parse("2023-01-10T12:00:00Z"),
                Instant.parse("2023-01-10T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                12,
                Instant.parse("2023-01-10T10:00:00Z"),
                Instant.parse("2023-01-10T12:00:00Z"),
                Instant.parse("2023-01-10T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                13,
                Instant.parse("2023-01-10T10:00:00Z"),
                Instant.parse("2023-01-10T12:00:00Z"),
                Instant.parse("2023-01-10T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                14,
                Instant.parse("2023-01-10T10:00:00Z"),
                Instant.parse("2023-01-10T12:00:00Z"),
                Instant.parse("2023-01-10T02:00:00Z")
            )
        )
        accessList.add(
            AccessListElement(
                15,
                Instant.parse("2023-01-10T10:00:00Z"),
                Instant.parse("2023-01-10T12:00:00Z"),
                Instant.parse("2023-01-10T02:00:00Z")
            )
        )
        return accessList.asReversed()
    }

}