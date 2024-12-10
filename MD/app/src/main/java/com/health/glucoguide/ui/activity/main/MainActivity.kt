package com.health.glucoguide.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import com.health.glucoguide.R
import com.health.glucoguide.databinding.ActivityMainBinding
import com.health.glucoguide.ui.activity.onboarding.OnBoardingActivity
import com.health.glucoguide.util.LocaleHelper
import com.health.glucoguide.util.NetworkUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val networkUtils: NetworkUtils by lazy { NetworkUtils(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupObserver()

        val language = viewModel.getLanguageSync()
        LocaleHelper.updateLocale(this, language)

        setupNavigationController(binding)
    }

    private fun setupObserver() {
        networkUtils.observe(this) { isConnected ->
            if (!isConnected) {
                showSnackbar(getString(R.string.network_connection_error))
            } else {
                showSnackbar(getString(R.string.network_connection_restored), R.color.army)
            }
        }

        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, OnBoardingActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                })
                finish()
            }
        }
    }

    private fun showSnackbar(errorMessage: String, color: Int = R.color.red) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(ContextCompat.getColor(this@MainActivity, color))
            setTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
            setActionTextColor(ContextCompat.getColor(this@MainActivity, R.color.white))
            anchorView = findViewById(R.id.bottom_navigation)
            setAction(getString(R.string.ok)) {
                dismiss()
            }
        }.show()
    }

    private fun setupNavigationController(binding: ActivityMainBinding) {
        val navView: BottomNavigationView = binding.bottomNavigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.inputDataFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.inputDataAdvancedFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.resultFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.newsFragment -> {
                    navView.visibility = View.GONE
                }
                R.id.editProfileFragment -> {
                    navView.visibility = View.GONE
                }
                else -> {
                    navView.visibility = View.VISIBLE
                }
            }
        }
    }
}