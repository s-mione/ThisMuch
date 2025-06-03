package com.smione.thismuch.ui.activity

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import androidx.appcompat.app.AppCompatActivity
import com.smione.thismuch.R
import com.smione.thismuch.model.repository.accesslog.AccessLogRepositoryInterface
import com.smione.thismuch.model.repository.accesslog.RoomAccessLogRepository
import com.smione.thismuch.model.repository.accesslog.database.RoomAccessLogDatabaseProvider
import com.smione.thismuch.service.timenotification.TimeNotificationService
import com.smione.thismuch.ui.activitycontract.MainActivityContract
import com.smione.thismuch.ui.fragment.AccessLogListFragment
import com.smione.thismuch.ui.fragment.MainFragment
import timber.log.Timber


interface ServiceConnectionCallback {
    fun onServiceConnected()
}

class MainActivity : AppCompatActivity(), MainActivityContract {

    private lateinit var accessLogRepository: AccessLogRepositoryInterface

    private lateinit var timeNotificationService: TimeNotificationService
    private var isBound = false

    private var serviceConnectionCallback: ServiceConnectionCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.plant(Timber.DebugTree())
        Timber.v("MainActivity onCreate")
        super.onCreate(savedInstanceState)
        accessLogRepository = RoomAccessLogRepository(RoomAccessLogDatabaseProvider())

        Intent(this, TimeNotificationService::class.java).also { intent ->
            bindService(intent, connection, BIND_AUTO_CREATE)
        }

        setContentView(R.layout.activity_main)
        serviceConnectionCallback = object : ServiceConnectionCallback {
            override fun onServiceConnected() {
                replaceFragmentToAccessLogListFragment()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isBound) {
            unbindService(connection)
            isBound = false
        }
    }

    override fun replaceFragmentToAccessLogListFragment() {
        Timber.v("MainActivity replaceFragmentToAccessListFragment")
        val fragment =
            AccessLogListFragment.newInstance(accessLogRepository, timeNotificationService)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment, AccessLogListFragment.TAG)
            .commit()
    }

    override fun replaceFragmentToMainFragment() {
        Timber.v("MainActivity replaceFragmentToMainFragment")
        val fragment = MainFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment, MainFragment.TAG)
            .commit()
    }

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Timber.v("MainActivity onServiceConnected")
            val binder = service as TimeNotificationService.LocalBinder
            timeNotificationService = binder.getService()
            isBound = true
            serviceConnectionCallback?.onServiceConnected()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }
}