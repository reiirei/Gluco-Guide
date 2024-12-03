package com.health.glucoguide.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.health.glucoguide.adapter.HistoryAdapter
import com.health.glucoguide.databinding.FragmentHistoryBinding
import com.health.glucoguide.models.UserData
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupRecyclerView()
        setupData()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
    }

    private fun setupRecyclerView() {
        val users = List(5) {
            UserData().apply {
                date = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault()).format(Date())
                hemoglobinLevel = 12.0
                glucoseLevel = 120
            }
        }

        val adapter = HistoryAdapter()
        binding.rvHistory.layoutManager = LinearLayoutManager(context)
        binding.rvHistory.adapter = adapter
        adapter.submitList(users)
    }

    private fun setupData() {
        val simpleDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy", Locale.getDefault())
        val currentDate = Date()

        val user = UserData()
        user.date = simpleDateFormat.format(currentDate)
    }
}