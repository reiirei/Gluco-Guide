package com.health.glucoguide.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.health.glucoguide.R
import com.health.glucoguide.databinding.HistoryItemBinding
import com.health.glucoguide.models.UserData

class HistoryAdapter : ListAdapter<UserData, HistoryAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding: HistoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserData) {
            binding.apply {
                tvDate.text = user.date
                tvResultHemoglobin.text = user.hemoglobinLevel.toString()
                tvResultGlucoseLevel.text = user.glucoseLevel.toString()
                tvResultCheck.text = ContextCompat.getString(binding.root.context, R.string.diabete_result)
            }
        }
    }

    companion object DIFF_CALLBACK : DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean {
            return oldItem == newItem
        }
    }

}