package com.health.glucoguide.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.health.glucoguide.R
import com.health.glucoguide.databinding.FragmentNewsBinding
import com.health.glucoguide.models.WebLink

class NewsFragment : Fragment() {

    private var _binding: FragmentNewsBinding? = null
    private val binding get() = _binding!!
    private val args: NewsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arg = args.news

        setupToolbar()
        setupWebView(arg)
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
        toolbar.setNavigationIcon(R.drawable.ic_arrow_30)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupWebView(args: WebLink) {
        val webView = binding.webView
        webView.settings.apply {
            domStorageEnabled = true
            allowFileAccess = false
        }
        webView.webViewClient = WebViewClient()
        webView.loadUrl(args.url)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}