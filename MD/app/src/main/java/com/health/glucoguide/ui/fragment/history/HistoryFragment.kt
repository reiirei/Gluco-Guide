package com.health.glucoguide.ui.fragment.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.health.glucoguide.R
import com.health.glucoguide.adapter.HistoryAdapter
import com.health.glucoguide.databinding.FragmentHistoryBinding
import com.health.glucoguide.util.ProgressDialogUtil
import dagger.hilt.android.AndroidEntryPoint

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

        val adapter = HistoryAdapter()

        binding.rvHistory.layoutManager = LinearLayoutManager(context)
        binding.rvHistory.adapter = adapter

        viewModel.getSession().observe(viewLifecycleOwner) { session ->
            val token = session.token.toString()
            viewModel.getHistories(token)
        }

        setupToolbar()
        setupObserver(adapter)
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
    }

    private fun setupObserver(adapter: HistoryAdapter) {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) progressDialog.showLoading() else progressDialog.hideLoading()
        }

        viewModel.listHistories.observe(viewLifecycleOwner) { histories ->

            adapter.submitList(histories)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            showSnackbar(errorMessage)
        }
    }

    private fun showSnackbar(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.black))
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            anchorView = requireActivity().findViewById(R.id.bottom_navigation)
            setAction("Retry") {
                viewModel.getHistories(viewModel.getSession().value?.token.toString())
            }
        }.show()
    }
}
