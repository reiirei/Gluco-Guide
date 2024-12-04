package com.health.glucoguide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.health.glucoguide.databinding.HistoryItemBinding
import com.health.glucoguide.models.HistoriesItem
import java.text.SimpleDateFormat
import java.util.Locale

class HistoryAdapter : ListAdapter<HistoriesItem, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: HistoriesItem) {
            binding.apply {
                val inputDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val outputDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy, HH:mm", Locale.getDefault())
                val date = user.tanggalCek?.let {
                    inputDateFormat.parse(it)
                }
                val formattedDate = date?.let {
                    outputDateFormat.format(it)
                }
                tvDate.text = formattedDate
                tvResultKeluhan.text = user.keluhan.toString()
                tvResultCheck.text = user.diagnosa
            }
        }
    }

    companion object DIFF_CALLBACK : DiffUtil.ItemCallback<HistoriesItem>() {
        override fun areItemsTheSame(oldItem: HistoriesItem, newItem: HistoriesItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HistoriesItem, newItem: HistoriesItem): Boolean {
            return oldItem == newItem
        }
    }

}