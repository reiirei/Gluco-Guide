package com.health.glucoguide.ui.activity.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.health.glucoguide.R
import com.health.glucoguide.util.ProgressDialogUtil
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.databinding.ActivitySignupBinding
import com.health.glucoguide.ui.activity.login.LoginActivity
import com.health.glucoguide.util.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val progressDialog by lazy { ProgressDialogUtil(this) }
    private val viewModel: SignUpViewModel by viewModels()

    private lateinit var email: TextInputEditText
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = binding.tiEmail
        username = binding.tiUsername
        password = binding.tiPassword

        setupAction()
        sendUserDataRegisterRequest()
    }

    private fun sendUserDataRegisterRequest() {
        binding.btnNextSignup.setOnClickListener {
            val email = email.text.toString().trim()
            val username = username.text.toString().trim()
            val password = password.text.toString().trim()

            val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"

            if (email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                val emptyFields = getString(R.string.all_fields_must_not_be_empty)
                showSnackbar(emptyFields)
            } else if (!email.matches(emailPattern.toRegex())) {
                val emailInvalid = getString(R.string.invalid_email_address)
                showSnackbar(emailInvalid)
            } else {
                viewModel.registerAccount(username, email, password).observe(this) { response ->
                    when (response) {
                        is ResultState.Loading -> {
                            progressDialog.showLoading()
                        }
                        is ResultState.Success -> {
                            progressDialog.hideLoading()
                            val successSignup = getString(R.string.sign_up_success)
                            showToast(successSignup, this@SignUpActivity)
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is ResultState.Error -> {
                            progressDialog.hideLoading()
                            showSnackbar(response.error)
                        }
                    }
                }
            }

        }
    }

    private fun showSnackbar(errorMessage: String) {
        Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_SHORT).apply {
            setBackgroundTint(ContextCompat.getColor(this@SignUpActivity, R.color.black))
            setTextColor(ContextCompat.getColor(this@SignUpActivity, R.color.white))
            setAction("OK") { dismiss() }
        }.show()
    }

    private fun setupAction() {
        binding.login.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}