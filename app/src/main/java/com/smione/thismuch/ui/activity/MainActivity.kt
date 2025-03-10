package com.smione.thismuch.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.smione.thismuch.R
import com.smione.thismuch.model.repository.AccessLogRepositoryInterface
import com.smione.thismuch.model.repository.RoomAccessLogRepository
import com.smione.thismuch.service.TimeNotificationService
import com.smione.thismuch.ui.activitycontract.MainActivityContract
import com.smione.thismuch.ui.fragment.AccessLogListFragment
import com.smione.thismuch.ui.fragment.MainFragment

class MainActivity : AppCompatActivity(), MainActivityContract {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("MainActivity", "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragmentToMainFragment()
    }

    override fun replaceFragmentToAccessLogListFragment() {
        Log.v("MainActivity", "replaceFragmentToAccessListFragment")
        val fragment = AccessLogListFragment.newInstance(MockAccessLogRepository())
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