package com.smione.thismuch.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.smione.thismuch.R
import com.smione.thismuch.repository.MockAccessRepository
import com.smione.thismuch.ui.activitycontract.MainActivityContract
import com.smione.thismuch.ui.fragment.AccessListFragment
import com.smione.thismuch.ui.fragment.MainFragment

class MainActivity : AppCompatActivity(), MainActivityContract {

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.v("MainActivity", "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragmentToMainFragment()
    }

    override fun replaceFragmentToAccessListFragment() {
        Log.v("MainActivity", "replaceFragmentToAccessListFragment")
        val fragment = AccessListFragment.newInstance(MockAccessRepository())
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment, AccessListFragment.TAG)
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