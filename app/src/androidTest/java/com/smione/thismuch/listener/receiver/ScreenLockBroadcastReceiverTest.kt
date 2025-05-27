package com.smione.thismuch.listener.receiver

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.smione.thismuch.receivercontract.ScreenLockBroadcastReceiverContract
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class ScreenLockBroadcastReceiverTest {

    private val mockScreenLockBroadcastReceiverContract: ScreenLockBroadcastReceiverContract =
        mock(ScreenLockBroadcastReceiverContract::class.java)


    @Test
    fun testReceiverItCallsOnScreenLockIfActionScreenOff() {
        val receiver = ScreenLockBroadcastReceiver(mockScreenLockBroadcastReceiverContract)
        val context = ApplicationProvider.getApplicationContext<Context>()

        doNothing().`when`(mockScreenLockBroadcastReceiverContract).onScreenLock()

        val intent = Intent(Intent.ACTION_SCREEN_OFF)

        receiver.onReceive(context, intent)

        verify(mockScreenLockBroadcastReceiverContract).onScreenLock()
    }

    @Test
    fun testReceiverItDoesNotCallOnScreenLockIfItIsNotActionScreenOff() {
        val receiver = ScreenLockBroadcastReceiver(mockScreenLockBroadcastReceiverContract)
        val context = ApplicationProvider.getApplicationContext<Context>()

        val intent = Intent(Intent.ACTION_SCREEN_ON)

        receiver.onReceive(context, intent)

        verify(mockScreenLockBroadcastReceiverContract, never()).onScreenLock()
    }
}
