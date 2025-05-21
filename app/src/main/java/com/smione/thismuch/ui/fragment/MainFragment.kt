package com.smione.thismuch.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smione.thismuch.databinding.FragmentMainBinding
import com.smione.thismuch.utils.init.MainActivityUtils
import timber.log.Timber

class MainFragment() : Fragment() {

    private lateinit var context: Context
    private lateinit var binding: FragmentMainBinding

    companion object {
        const val TAG = "MainFragment"

        fun newInstance(): MainFragment = MainFragment()
    }

    override fun onAttach(context: Context) {
        Timber.v("MainFragment onAttach")
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.v("MainFragment onCreateView")
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Timber.v("MainFragment onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        MainActivityUtils.askToIgnoreBatteryOptimization(requireContext())
        MainActivityUtils.startTimeNotificationServiceIfNotRunning(context)
    }
}