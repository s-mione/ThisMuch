package com.smione.thismuch.listener.receiver

import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.smione.thismuch.receivercontract.ScreenUnlockBroadcastReceiverContract
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class ScreenUnlockBroadcastReceiverTest {

    private val mockScreenUnlockBroadcastReceiverContract: ScreenUnlockBroadcastReceiverContract =
        mock(ScreenUnlockBroadcastReceiverContract::class.java)


    @Test
    fun testReceiverItCallsOnScreenLockIfActionScreenOff() {
        val receiver = ScreenUnlockBroadcastReceiver(mockScreenUnlockBroadcastReceiverContract)
        val context = ApplicationProvider.getApplicationContext<Context>()

        doNothing().`when`(mockScreenUnlockBroadcastReceiverContract).onScreenUnlock()

        val intent = Intent(Intent.ACTION_SCREEN_ON)

        receiver.onReceive(context, intent)

        verify(mockScreenUnlockBroadcastReceiverContract).onScreenUnlock()
    }

    @Test
    fun testReceiverItDoesNotCallOnScreenLockIfItIsNotActionScreenOff() {
        val receiver = ScreenUnlockBroadcastReceiver(mockScreenUnlockBroadcastReceiverContract)
        val context = ApplicationProvider.getApplicationContext<Context>()

        val intent = Intent(Intent.ACTION_SCREEN_OFF)

        receiver.onReceive(context, intent)

        verify(mockScreenUnlockBroadcastReceiverContract, never()).onScreenUnlock()
    }
}
