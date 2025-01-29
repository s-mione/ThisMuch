package com.smione.thismuch.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smione.thismuch.databinding.FragmentMainBinding
import com.smione.thismuch.receiver.ScreenLockUnlockBroadcastReceiver
import com.smione.thismuch.receivercontract.ScreenLockUnlockBroadcastReceiverContract

class MainFragment(): Fragment(), ScreenLockUnlockBroadcastReceiverContract {

    lateinit var screenLockUnlockBroadcastReceiver: ScreenLockUnlockBroadcastReceiver

    private lateinit var context: Context
    private lateinit var binding: FragmentMainBinding

    companion object {
        const val TAG = "MainFragment"

        fun newInstance(): MainFragment = MainFragment()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        this.context = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        screenLockUnlockBroadcastReceiver = ScreenLockUnlockBroadcastReceiver(this)
        screenLockUnlockBroadcastReceiver.register(this.context)
    }

    override fun onScreenLockUnlock() {
        binding.tvMain.text = "Screen Unlock"
    }
}