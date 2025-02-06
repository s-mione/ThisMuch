package com.smione.thismuch.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smione.thismuch.databinding.FragmentMainBinding
import com.smione.thismuch.service.TimeNotificationService
import com.smione.thismuch.ui.dialog.AskToIgnoreBatteryOptimizationDialog

class MainFragment() : Fragment() {

    private lateinit var context: Context
    private lateinit var binding: FragmentMainBinding

    companion object {
        const val TAG = "MainFragment"

        fun newInstance(): MainFragment = MainFragment()
    }

    override fun onAttach(context: Context) {
        Log.v("MainFragment", "onAttach")
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.v("MainFragment", "onCreateView")
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.v("MainFragment", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        AskToIgnoreBatteryOptimizationDialog(requireContext()).show()
        if (TimeNotificationService.isRunning.not()) {
            TimeNotificationService.isRunning = true
            val serviceIntent = Intent(context, TimeNotificationService::class.java)
            context.startService(serviceIntent)
        }
    }
}