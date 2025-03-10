package com.smione.thismuch.ui.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.smione.thismuch.R
import com.smione.thismuch.model.repository.AccessLogRepositoryInterface
import com.smione.thismuch.model.repository.RoomAccessLogRepository
import com.smione.thismuch.service.TimeNotificationService
import com.smione.thismuch.ui.activitycontract.MainActivityContract
import com.smione.thismuch.ui.fragment.AccessLogListFragment
import com.smione.thismuch.ui.fragment.MainFragment

interface ServiceConnectionCallback {
    fun onServiceConnected()
}

class MainActivity : AppCompatActivity(), MainActivityContract {

    private lateinit var accessLogRepository: AccessLogRepositoryInterface

    private lateinit var timeNotificationService: TimeNotificationService
    private var isBound = false

    private var serviceConnectionCallback: ServiceConnectionCallback? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            Log.v("MainActivity", "onServiceConnected")
            val binder = service as TimeNotificationService.LocalBinder
            timeNotificationService = binder.getService()
            isBound = true
            serviceConnectionCallback?.onServiceConnected()
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            isBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("MainActivity", "onCreate")
        super.onCreate(savedInstanceState)
        accessLogRepository = RoomAccessLogRepository(this)

        Intent(this, TimeNotificationService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
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
        Log.v("MainActivity", "replaceFragmentToAccessListFragment")
        val fragment =
            AccessLogListFragment.newInstance(accessLogRepository, timeNotificationService)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment, AccessLogListFragment.TAG)
            .commit()
    }

    override fun replaceFragmentToMainFragment() {
        Log.v("MainActivity", "replaceFragmentToMainFragment")
        val fragment = MainFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment, MainFragment.TAG)
            .commit()
    }
}