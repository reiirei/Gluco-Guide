package com.health.glucoguide.ui.fragment.editprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.health.glucoguide.R
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.databinding.FragmentEditProfileBinding
import com.health.glucoguide.models.UserInputProfile
import com.health.glucoguide.models.UserSession
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

            getUserData(session.token ?: "")
            setupAction()
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
                            showToast(result.error, requireContext())
                        }
                    }
                }
            }
        }
    }

    private fun validateUserData(username: String, password: String): Boolean {
        return if (username.isEmpty() || password.isEmpty()) {
            showToast(getString(R.string.all_fields_must_not_be_empty), requireContext())
            false
        } else {
            true
        }
    }

    private fun getUserData(token: String) {
        viewModel.getUserData(token)
            .observe(viewLifecycleOwner) { result ->
                when (result) {
                    is ResultState.Loading -> {
                        progressDialog.showLoading()
                    }

                    is ResultState.Success -> {
                        progressDialog.hideLoading()
                        binding.tiUsername.setText(result.data.user?.name)
                    }

                    is ResultState.Error -> {
                        progressDialog.hideLoading()
                        val errorMessage = result.error
                        showToast(errorMessage, requireContext())
                    }
                }
            }
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }

}