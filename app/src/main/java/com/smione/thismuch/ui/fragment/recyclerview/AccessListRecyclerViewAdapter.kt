package com.smione.thismuch.ui.fragment.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smione.thismuch.databinding.FragmentAccessItemBinding

class AccessListRecyclerViewAdapter(
    private val values: List<AccessListElement>)
    : RecyclerView.Adapter<AccessListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentAccessItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.itemNumber.text = item.itemNumber.toString()
        holder.timeOn.text = item.formattedTimeOn
        holder.timeOff.text = item.formattedTimeOff
        holder.totalTime.text = item.formattedTotalTime
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentAccessItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val itemNumber: TextView = binding.tvItemNumber
        val timeOn: TextView = binding.tvTimeOn
        val timeOff: TextView = binding.tvTimeOff
        val totalTime: TextView = binding.tvTotalTime
    }

}