package com.smione.thismuch.presenter

import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.model.converter.AccessLogListElementAccessLogEntityConverter
import com.smione.thismuch.model.converter.InstantDurationStringConverter
import com.smione.thismuch.model.element.AccessLogListElement
import com.smione.thismuch.model.repository.AccessLogRepositoryInterface
import com.smione.thismuch.model.repository.entity.AccessLogEntity
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import timber.log.Timber
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

class AccessLogRepositoryPresenterTest {

    @MockK(relaxed = true)
    private lateinit var view: AccessLogRepositoryContract.View

    @MockK(relaxed = true)
    private lateinit var repository: AccessLogRepositoryInterface

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val dispatcherProvider = object : DispatcherProvider {
        override fun io() = testDispatcher
        override fun main() = testDispatcher
    }

    private val scopeProvider = object : ScopeProvider {
        override fun supervisorJobProvider(coroutineDispatcher: CoroutineDispatcher) = testScope
    }

    private lateinit var presenter: AccessLogRepositoryPresenter

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Timber.uprootAll()
        presenter = AccessLogRepositoryPresenter(scopeProvider, dispatcherProvider, repository)
        presenter.bindView(view)
    }

    @Test
    fun `deleteAll triggers repository and notifies view`() = testScope.runTest {
        coEvery { repository.deleteAll() } just Runs

        presenter.deleteAll()
        testScheduler.advanceUntilIdle()

        coVerify { repository.deleteAll() }
        verify { view.onDeleteAll() }
    }

    @Test
    fun `saveAccessLogElement triggers repository`() = testScope.runTest {
        val element =
            AccessLogListElement(0, INSTANT_TIME_ON, INSTANT_TIME_OFF, TOTAL_TIME)
        val accessEntity =
            AccessLogListElementAccessLogEntityConverter.fromAccessListElementToAccessEntity(element)

        coEvery { repository.saveAccessLogEntity(any()) } just Runs

        presenter.saveAccessLogElement(element)
        testScheduler.advanceUntilIdle()

        coVerify { repository.saveAccessLogEntity(accessEntity) }
    }

    @Test
    fun `getAccessLogListIndexedByTimeDesc with empty list`() =
        testScope.runTest {

            coEvery { repository.getAccessLogListSortedByTimeDesc() } returns emptyList()

            presenter.getAccessLogListIndexedByTimeDesc()
            testScheduler.advanceUntilIdle()

            coVerify { repository.getAccessLogListSortedByTimeDesc() }
            verify { view.onGetAccessLogList(emptyList()) }
        }

    @Test
    fun `getAccessLogListIndexedByTimeDesc returns with the right order`() =
        testScope.runTest {
            val accessLogEntityList = getAccessLogEntityList()
            val accessLogElementList = accessLogElementList(accessLogEntityList)

            coEvery { repository.getAccessLogListSortedByTimeDesc() } returns accessLogEntityList

            presenter.getAccessLogListIndexedByTimeDesc()
            testScheduler.advanceUntilIdle()

            coVerify { repository.getAccessLogListSortedByTimeDesc() }
            verify { view.onGetAccessLogList(accessLogElementList) }
        }


    companion object {
        private val INSTANT_TIME_ON = Instant.now().minus(3, ChronoUnit.HOURS)
        private val INSTANT_TIME_OFF = Instant.now().minus(2, ChronoUnit.HOURS)

        private val INSTANT_TIME_ON_LATER = Instant.now().minus(1, ChronoUnit.HOURS)
        private val INSTANT_TIME_OFF_LATER = Instant.now()

        private val TOTAL_TIME = Duration.ofHours(1)

        private fun getAccessLogEntityList() = listOf(
            AccessLogEntity(
                InstantDurationStringConverter.fromInstantToString(
                    INSTANT_TIME_ON_LATER
                ),
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_OFF_LATER),
                InstantDurationStringConverter.fromDurationToString(TOTAL_TIME),
                1
            ),
            AccessLogEntity(
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_ON),
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_OFF),
                InstantDurationStringConverter.fromDurationToString(TOTAL_TIME),
                0
            )
        )

        private fun accessLogElementList(accessLogEntityList: List<AccessLogEntity>): List<AccessLogListElement> {
            var index = accessLogEntityList.size
            return accessLogEntityList.map {
                AccessLogListElementAccessLogEntityConverter.fromAccessEntityToAccessListElementWithIndex(
                    index--, it
                )
            }
        }
    }
}