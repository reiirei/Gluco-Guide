package com.health.glucoguide.ui.fragment.editprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.health.glucoguide.R
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.databinding.FragmentEditProfileBinding
import com.health.glucoguide.data.remote.request.UserInputProfile
import com.health.glucoguide.data.remote.response.UserSession
import com.health.glucoguide.util.ProgressDialogUtil
import com.health.glucoguide.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment : Fragment() {
    private lateinit var binding: FragmentEditProfileBinding
    private val viewModel: EditProfileViewModel by viewModels()
    private lateinit var userData: UserSession
    private val progressDialog by lazy { ProgressDialogUtil(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getSession().observe(viewLifecycleOwner) { session ->
            userData = UserSession(
                session.name,
                session.token,
                session.isLogin
            )

            viewModel.getUserData(userData.token ?: "")

            setupAction()
            setupObservers()
        }

        setupToolbar()
    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        toolbar.isTitleCentered = true
        toolbar.setNavigationIcon(R.drawable.ic_arrow_30)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun setupAction() {
        binding.btnSave.setOnClickListener {
            val username = binding.tiUsername.text.toString()
            val password = binding.tiPassword.text.toString()
            val token = userData.token ?: ""

            if (validateUserData(username, password)) {
                val userData = UserInputProfile(username, password)
                viewModel.setUserData(token, userData).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            progressDialog.showLoading()
                        }
                        is ResultState.Success -> {
                            progressDialog.hideLoading()
                            showToast(getString(R.string.profile_updated), requireContext())
                            navigateBack()
                        }
                        is ResultState.Error -> {
                            progressDialog.hideLoading()
                            val errorMessage = result.error
                            if (!errorMessage.contains("Invalid token")) {
                                showSnackbar(errorMessage)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun showSnackbar(errorMessage: String, anchorView: View? = null) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.black))
            setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
            anchorView?.let { this.anchorView = it }
            setAction("Retry") {
                viewModel.getUserData(viewModel.getSession().value?.token.toString())
            }
        }.show()
    }

    private fun validateUserData(username: String, password: String): Boolean {
        return if (username.isEmpty() || password.isEmpty()) {
            showSnackbar(getString(R.string.all_fields_must_not_be_empty))
            false
        } else {
            true
        }
    }

    private fun setupObservers() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                showLoading()
            } else {
                hideLoading()
            }
        }

        viewModel.username.observe(viewLifecycleOwner) { username ->
            updateUsernameUI(username)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                showSnackbar(errorMessage)
            }
        }
    }

    private fun showLoading() {
        progressDialog.showLoading()
    }

    private fun hideLoading() {
        progressDialog.hideLoading()
    }

    private fun updateUsernameUI(username: String) {
        binding.tiUsername.setText(username)
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }

}