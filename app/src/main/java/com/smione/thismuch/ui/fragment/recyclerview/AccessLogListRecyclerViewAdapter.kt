package com.smione.thismuch.ui.fragment.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.smione.thismuch.databinding.FragmentAccessItemBinding
import com.smione.thismuch.model.converter.InstantDurationStringConverter
import timber.log.Timber

class AccessLogListRecyclerViewAdapter(
    private val headers: List<String>,
    private var values: List<AccessLogListElement>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    fun updateValues(values: List<AccessLogListElement>, recyclerView: RecyclerView) {
        Timber.v("AccessLogListRecyclerViewAdapter updateValues: $values")
        recyclerView.post {
            val numberOfItemsAdded = values.size - this.values.size
            this.values = values
            notifyItemRangeInserted(0, numberOfItemsAdded)
        }
    }

    fun deleteAll(recyclerView: RecyclerView) {
        Timber.v("AccessLogListRecyclerViewAdapter delete all")
        recyclerView.post {
            notifyItemRangeRemoved(0, values.size)
            this.values = emptyList()
        }
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
            Timber.v("AccessLogListRecyclerViewAdapter ItemViewHolder bind: $item")
            binding.tvItemNumber.text = (values.size - position + 1).toString()
            binding.tvTimeOn.text =
                item.timeOn.let { InstantDurationStringConverter.fromInstantToFormattedString(it) }
            binding.tvTimeOff.text =
                item.timeOff.let { InstantDurationStringConverter.fromInstantToFormattedString(it) }
            binding.tvTotalTime.text =
                item.totalTime.let { InstantDurationStringConverter.fromDurationToFormattedString(it) }
        }
    }
}