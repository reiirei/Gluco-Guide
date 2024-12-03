package com.health.glucoguide.ui.activity.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.health.glucoguide.util.ProgressDialogUtil
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.databinding.ActivitySignUpBinding
import com.health.glucoguide.ui.activity.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val progressDialog by lazy { ProgressDialogUtil(this) }
    private val viewModel: SignUpViewModel by viewModels()

    private lateinit var email: TextInputEditText
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = binding.tiEmail
        username = binding.tiUsername
        password = binding.tiPassword

        setupAction()
        sendUserDataRegisterRequest()
    }

    private fun sendUserDataRegisterRequest() {
        binding.btnNext.setOnClickListener {
            val email = email.text.toString().trim()
            val username = username.text.toString().trim()
            val password = password.text.toString().trim()

            val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"

            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                val emptyFields = getString(com.health.glucoguide.R.string.all_fields_must_not_be_empty)
                showToast(emptyFields)
            } else if (!email.matches(emailPattern.toRegex())) {
                val emailInvalid = getString(com.health.glucoguide.R.string.invalid_email_address)
                showToast(emailInvalid)
            } else {
                viewModel.registerAccount(username, email, password).observe(this) { response ->
                    when (response) {
                        is ResultState.Loading -> {
                            progressDialog.showLoading()
                        }
                        is ResultState.Success -> {
                            progressDialog.hideLoading()
                            val successSignup = getString(com.health.glucoguide.R.string.sign_up_success)
                            showToast(successSignup)
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is ResultState.Error -> {
                            progressDialog.hideLoading()
                            showToast(response.error)
                        }
                    }
                }
            }

        }
    }

    private fun showToast(toast: String) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }

    private fun setupAction() {
        binding.login.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}