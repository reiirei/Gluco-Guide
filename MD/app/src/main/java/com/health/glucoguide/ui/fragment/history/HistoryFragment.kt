package com.health.glucoguide.ui.fragment.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.health.glucoguide.adapter.HistoryAdapter
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.databinding.FragmentHistoryBinding
import com.health.glucoguide.models.HistoriesItem
import com.health.glucoguide.models.UserData
import com.health.glucoguide.util.ProgressDialogUtil
import com.health.glucoguide.util.showToast
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HistoryViewModel by viewModels()
    private val progressDialog by lazy { ProgressDialogUtil(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { session ->
            val token = session.token.toString()
            getHistories(token)
        }

        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
    }

    private fun setupRecyclerView(users: List<HistoriesItem?>?) {
        val adapter = HistoryAdapter()

        binding.rvHistory.layoutManager = LinearLayoutManager(context)
        binding.rvHistory.adapter = adapter
        adapter.submitList(users)
    }

    private fun getHistories(token: String) {
        viewModel.getHistories(token).observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResultState.Loading -> {
                    progressDialog.showLoading()
                }
                is ResultState.Success -> {
                    progressDialog.hideLoading()
                    val listHistory = result.data.histories
                    setupRecyclerView(listHistory)
                }
                is ResultState.Error -> {
                    progressDialog.hideLoading()
                    val errorMessage = result.error
                    showToast(errorMessage, requireContext())
                }
            }
        }
    }
}