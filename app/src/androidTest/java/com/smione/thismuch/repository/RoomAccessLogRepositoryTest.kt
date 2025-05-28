package com.smione.thismuch.repository

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.smione.thismuch.model.converter.InstantDurationStringConverter
import com.smione.thismuch.model.repository.accesslog.RoomAccessLogRepository
import com.smione.thismuch.model.repository.accesslog.database.AccessLogDatabaseProvider
import com.smione.thismuch.model.repository.accesslog.database.RoomAccessLogDatabase
import com.smione.thismuch.model.repository.accesslog.database.entity.AccessLogEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

@RunWith(AndroidJUnit4::class)
class RoomAccessLogRepositoryTest {

    private val logMessages = mutableListOf<String>()

    private lateinit var repository: RoomAccessLogRepository

    private val accessLogDatabaseProvider = object : AccessLogDatabaseProvider {
        override fun main(applicationContext: Context) = Room.inMemoryDatabaseBuilder(
            applicationContext,
            RoomAccessLogDatabase::class.java
        ).allowMainThreadQueries().build().roomAccessLogDao()
    }

    companion object {
        val INSTANT_TIME_ON: Instant = Instant.now().minus(3, ChronoUnit.HOURS)
        val INSTANT_TIME_OFF: Instant = Instant.now().minus(2, ChronoUnit.HOURS)

        val INSTANT_TIME_ON_LATER: Instant = Instant.now().minus(2, ChronoUnit.HOURS)
        val INSTANT_TIME_OFF_LATER: Instant = Instant.now()

        val INSTANT_TIME_ON_EARLIER: Instant = Instant.now().minus(10, ChronoUnit.HOURS)
        val INSTANT_TIME_OFF_EARLIER: Instant = Instant.now().minus(7, ChronoUnit.HOURS)

        val TOTAL_TIME_ONE: Duration = Duration.ofHours(1)
        val TOTAL_TIME_TWO: Duration = Duration.ofHours(2)
        val TOTAL_TIME_THREE: Duration = Duration.ofHours(3)

        fun getAccessLogEntityOne(): AccessLogEntity =
            AccessLogEntity(
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_ON),
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_OFF),
                InstantDurationStringConverter.fromDurationToString(TOTAL_TIME_ONE),
                1
            )

        fun getAccessLogEntityTwo(): AccessLogEntity =
            AccessLogEntity(
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_ON_LATER),
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_OFF_LATER),
                InstantDurationStringConverter.fromDurationToString(TOTAL_TIME_TWO),
                2
            )

        fun getAccessLogEntityThree(): AccessLogEntity =
            AccessLogEntity(
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_ON_EARLIER),
                InstantDurationStringConverter.fromInstantToString(INSTANT_TIME_OFF_EARLIER),
                InstantDurationStringConverter.fromDurationToString(TOTAL_TIME_THREE),
                3
            )
    }

    @Before
    fun setup() {
        repository = RoomAccessLogRepository(accessLogDatabaseProvider)

        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                logMessages.add(message)
            }
        })
    }

    @After
    fun tearDown() {
        Timber.uprootAll()
    }

    @Test
    fun testSaveAndRetrieveAccessLog() = runBlocking {
        val accessLogEntityOne = getAccessLogEntityOne()
        val accessLogEntityTwo = getAccessLogEntityTwo()

        repository.initDatabase(ApplicationProvider.getApplicationContext())

        repository.saveAccessLogEntity(accessLogEntityOne)
        repository.saveAccessLogEntity(accessLogEntityTwo)

        val logs = repository.getAccessLogList()

        assertEquals(
            setOf(accessLogEntityOne, accessLogEntityTwo),
            logs.toSet()
        )
    }

    @Test
    fun testSaveTwoEntitiesWithTheSameIdReturnsError() {
        val accessLogEntityOne = getAccessLogEntityOne()

        repository.initDatabase(ApplicationProvider.getApplicationContext())

        val result = assertThrows(SQLiteConstraintException::class.java) {
            runBlocking {
                repository.saveAccessLogEntity(accessLogEntityOne)
                repository.saveAccessLogEntity(accessLogEntityOne)
            }
        }
    }

    @Test
    fun testGetAccessLogListSortedByTimeDescRetrievesTheEntitiesInTheRightOrder() = runBlocking {
        val accessLogEntityOne = getAccessLogEntityOne()
        val accessLogEntityTwo = getAccessLogEntityTwo()
        val accessLogEntityThree = getAccessLogEntityThree()

        repository.initDatabase(ApplicationProvider.getApplicationContext())

        repository.saveAccessLogEntity(accessLogEntityThree)
        repository.saveAccessLogEntity(accessLogEntityOne)
        repository.saveAccessLogEntity(accessLogEntityTwo)

        val logs = repository.getAccessLogListSortedByTimeDesc()

        assertEquals(
            listOf(accessLogEntityTwo, accessLogEntityOne, accessLogEntityThree),
            logs
        )
    }

    @Test
    fun testDeleteAllAccessLogs() = runBlocking {
        val accessLogEntityOne = getAccessLogEntityOne()
        val accessLogEntityTwo = getAccessLogEntityTwo()
        val accessLogEntityThree = getAccessLogEntityThree()

        repository.initDatabase(ApplicationProvider.getApplicationContext())

        repository.saveAccessLogEntity(accessLogEntityOne)
        repository.saveAccessLogEntity(accessLogEntityTwo)
        repository.saveAccessLogEntity(accessLogEntityThree)

        repository.deleteAll()

        val logs = repository.getAccessLogList()
        assertEquals(0, logs.size)
    }

    @Test
    fun testSaveAccessLogEntityWithoutInitializeIt() = runBlocking {
        val accessLogEntityOne = getAccessLogEntityOne()

        repository.saveAccessLogEntity(accessLogEntityOne)

        assertTrue(logMessages.contains("RoomAccessLogRepository database is not initialized"))
    }

    @Test
    fun testGetAccessLogListWithoutInitializeIt() = runBlocking {
        val result = repository.getAccessLogList()

        assertTrue(result.isEmpty())
        assertTrue(logMessages.contains("RoomAccessLogRepository database is not initialized"))
    }

    @Test
    fun testGetAccessLogListSortedByTimeDescWithoutInitializeIt() = runBlocking {
        val result = repository.getAccessLogListSortedByTimeDesc()

        assertTrue(result.isEmpty())
        assertTrue(logMessages.contains("RoomAccessLogRepository database is not initialized"))
    }

    @Test
    fun testDeleteAllWithoutInitializeIt() = runBlocking {
        repository.deleteAll()

        assertTrue(logMessages.contains("RoomAccessLogRepository database is not initialized"))
    }
}