package com.smione.thismuch.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.smione.thismuch.databinding.FragmentAccessListBinding
import com.smione.thismuch.repositorycontract.AccessLogRepositoryContract
import com.smione.thismuch.ui.fragment.recyclerview.AccessLogListRecyclerViewAdapter

class AccessLogListFragment(private val accessRepository: AccessLogRepositoryContract) :
    Fragment() {

    private lateinit var binding: FragmentAccessListBinding

    companion object {
        const val TAG = "AccessListFragment"
        fun newInstance(accessRepository: AccessLogRepositoryContract): AccessLogListFragment =
            AccessLogListFragment(accessRepository)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentAccessListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val headers = accessRepository.getHeaders()
        val accessList = accessRepository.getAccessList()
        binding.rvList.adapter = AccessLogListRecyclerViewAdapter(headers, accessList)
    }
}