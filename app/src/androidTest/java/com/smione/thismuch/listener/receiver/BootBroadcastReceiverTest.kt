import android.content.Context
import android.content.Intent
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.smione.thismuch.listener.receiver.BootBroadcastReceiver
import com.smione.thismuch.listener.receiver.handler.BootActionHandlerInterface
import junit.framework.TestCase.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BootBroadcastReceiverTest {

    @Test
    fun testBootBroadcastReceiver_startsServiceIntent() {
        val fakeBootActionHandler = FakeBootActionHandler()
        val receiver = BootBroadcastReceiver(fakeBootActionHandler)

        val context = ApplicationProvider.getApplicationContext<Context>()

        val intent = Intent(Intent.ACTION_BOOT_COMPLETED)

        receiver.onReceive(context, intent)

        assertTrue(fakeBootActionHandler.wasCalled)
    }

    private inner class FakeBootActionHandler : BootActionHandlerInterface {
        var wasCalled = false

        override fun handleBoot(context: Context) {
            wasCalled = true
        }
    }
}
