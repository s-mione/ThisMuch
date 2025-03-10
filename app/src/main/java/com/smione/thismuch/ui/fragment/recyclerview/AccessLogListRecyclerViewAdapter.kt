package com.smione.thismuch.ui.fragment.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smione.thismuch.databinding.FragmentAccessItemBinding
import com.smione.thismuch.model.converter.InstantStringConverter

class AccessLogListRecyclerViewAdapter(
    private val headers: List<String>,
    private val values: List<AccessLogListElement>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_HEADER) {
            val binding = FragmentAccessItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            HeaderViewHolder(binding)
        } else {
            val binding = FragmentAccessItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ItemViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HeaderViewHolder) {
            holder.bind(headers)
        } else if (holder is ItemViewHolder) {
            val item = values[position - 1]
            holder.bind(position, item)
        }
    }

    override fun getItemCount(): Int = values.size + 1

    inner class HeaderViewHolder(private val binding: FragmentAccessItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(headers: List<String>) {
            binding.tvItemNumber.text = headers[0]
            binding.tvTimeOn.text = headers[1]
            binding.tvTimeOff.text = headers[2]
            binding.tvTotalTime.text = headers[3]
        }
    }

    inner class ItemViewHolder(private val binding: FragmentAccessItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int, item: AccessLogListElement) {
            binding.tvItemNumber.text = position.toString()
            binding.tvTimeOn.text = item.formattedTimeOn
            binding.tvTimeOff.text = item.formattedTimeOff
            binding.tvTotalTime.text = item.formattedTotalTime
        }
    }

}