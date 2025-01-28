package com.smione.thismuch.ui.activity

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.smione.thismuch.R
import com.smione.thismuch.ui.activitycontract.MainActivityContract
import com.smione.thismuch.ui.fragment.MainFragment

class MainActivity : FragmentActivity(), MainActivityContract {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragmentToMainFragment()
    }

    override fun replaceFragmentToMainFragment() {
        val fragment = MainFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, fragment, MainFragment.TAG)
            .commit()
    }
}