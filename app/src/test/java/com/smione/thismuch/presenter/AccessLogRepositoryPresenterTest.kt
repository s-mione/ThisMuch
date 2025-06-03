package com.smione.thismuch.presenter

import com.smione.thismuch.InstantCommonTestHelper
import com.smione.thismuch.contract.AccessLogRepositoryContract
import com.smione.thismuch.model.converter.AccessLogListElementAccessLogEntityConverter
import com.smione.thismuch.model.repository.accesslog.AccessLogRepositoryInterface
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListElement
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
            AccessLogListElement(
                0,
                InstantCommonTestHelper.INSTANT_TIME_ON,
                InstantCommonTestHelper.INSTANT_TIME_OFF,
                InstantCommonTestHelper.TOTAL_TIME
            )
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

            presenter.getAccessLogList()
            testScheduler.advanceUntilIdle()

            coVerify { repository.getAccessLogListSortedByTimeDesc() }
            verify { view.onGetAccessLogList(emptyList()) }
        }

    @Test
    fun `getAccessLogListIndexedByTimeDesc returns with the right order`() =
        testScope.runTest {
            val accessLogEntityList = InstantCommonTestHelper.getAccessLogEntityList()
            val accessLogElementList =
                InstantCommonTestHelper.accessLogElementList(accessLogEntityList)

            coEvery { repository.getAccessLogListSortedByTimeDesc() } returns accessLogEntityList

            presenter.getAccessLogList()
            testScheduler.advanceUntilIdle()

            coVerify { repository.getAccessLogListSortedByTimeDesc() }
            verify { view.onGetAccessLogList(accessLogElementList) }
        }
}