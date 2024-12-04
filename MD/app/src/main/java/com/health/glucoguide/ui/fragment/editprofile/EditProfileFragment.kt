package com.health.glucoguide.ui.fragment.editprofile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.health.glucoguide.R
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.databinding.FragmentEditProfileBinding
import com.health.glucoguide.models.UserSession
import com.health.glucoguide.util.ProgressDialogUtil
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
                session.email,
                session.name,
                session.password,
                session.token,
                session.isLogin
            )

            getUserData(session.token ?: "")
        }


        setupToolbar()
        setupAction()
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
            val email = binding.tiEmail.text.toString()
            val username = binding.tiUsername.text.toString()
            val password = binding.tiPassword.text.toString()
            val token = userData.token ?: ""

            if (validateUserData(email, username, password)) {
                val userData = UserSession(email, username, password, token)
                viewModel.setUserData(token, userData).observe(viewLifecycleOwner) { result ->
                    when (result) {
                        is ResultState.Loading -> {
                            progressDialog.showLoading()
                        }
                        is ResultState.Success -> {
                            progressDialog.hideLoading()
                            showToast(getString(R.string.profile_updated))
                            navigateBack()
                        }
                        is ResultState.Error -> {
                            progressDialog.hideLoading()
                            showToast(result.error)
                        }
                    }
                }
            }
        }
    }

    private fun validateUserData(email: String, username: String, password: String): Boolean {
        return if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showToast(getString(R.string.all_fields_must_not_be_empty))
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
                        binding.tiEmail.setText(result.data.user?.email)
                        binding.tiUsername.setText(result.data.user?.name)
                    }

                    is ResultState.Error -> {
                        progressDialog.hideLoading()
                        val errorMessage = result.error
                        showToast(errorMessage)
                    }
                }
            }
    }

    private fun showToast(toast: String) {
        Toast.makeText(requireContext(), toast, Toast.LENGTH_SHORT).show()
    }

    private fun navigateBack() {
        findNavController().navigateUp()
    }

}