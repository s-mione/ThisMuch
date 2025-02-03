package com.smione.thismuch.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smione.thismuch.R
import com.smione.thismuch.databinding.FragmentMainBinding
import com.smione.thismuch.notification.NotificationUtils
import com.smione.thismuch.notification.TimeNotificationUtils
import com.smione.thismuch.receiver.ScreenUnlockBroadcastReceiver
import com.smione.thismuch.receivercontract.ScreenUnlockBroadcastReceiverContract
import java.time.Instant
import java.time.ZoneId

class MainFragment() : Fragment(), ScreenUnlockBroadcastReceiverContract {

    lateinit var screenUnlockBroadcastReceiver: ScreenUnlockBroadcastReceiver

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
        screenUnlockBroadcastReceiver = ScreenUnlockBroadcastReceiver(this)
        screenUnlockBroadcastReceiver.register(this.context)
        NotificationUtils.createNotificationChannel(
            this.context, NotificationUtils.DEFAULT_CHANNEL_ID, this.getString(R.string.app_name),
            this.getString(R.string.app_name), NotificationUtils.IMPORTANCE_DEFAULT
        )
    }

    override fun onScreenUnlock() {
        Log.v("MainFragment", "onScreenUnlock")
        val time: Instant = Instant.now()
        val hour = time.atZone(ZoneId.systemDefault()).hour
        val minute = time.atZone(ZoneId.systemDefault()).minute
        Log.v("MainFragment", "onScreenUnlock: at time [${hour}:${minute}]")
        TimeNotificationUtils.sendNotificationForTime(this.context, this, hour, minute)
        binding.tvMain.text = "Screen Unlock at ${hour}:${minute}"
    }
}