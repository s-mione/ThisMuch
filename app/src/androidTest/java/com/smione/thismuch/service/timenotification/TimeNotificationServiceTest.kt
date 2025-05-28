package com.smione.thismuch.service.timenotification

import android.content.Intent
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ServiceTestRule
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TimeNotificationServiceTest {

    @get:Rule
    private val serviceRule = ServiceTestRule()
    private lateinit var intent: Intent

    @Before
    fun setUp() {
        intent = Intent(
            InstrumentationRegistry.getInstrumentation().targetContext,
            TimeNotificationService::class.java
        )
    }

    @Test
    fun testServiceLifecycle() {
        val binder = serviceRule.bindService(intent) as TimeNotificationService.LocalBinder
        val service = binder.getService()

        assertTrue(TimeNotificationService.isRunning)

        service.onDestroy()

        assertFalse(TimeNotificationService.isRunning)
    }
}