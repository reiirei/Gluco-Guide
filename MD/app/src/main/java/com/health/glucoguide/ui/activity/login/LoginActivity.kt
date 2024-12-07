package com.health.glucoguide.ui.activity.login

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
import com.health.glucoguide.databinding.ActivityLoginBinding
import com.health.glucoguide.data.remote.response.UserSession
import com.health.glucoguide.ui.activity.MainActivity
import com.health.glucoguide.ui.activity.signup.SignUpActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    private val progressDialog by lazy { ProgressDialogUtil(this) }
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = binding.tiEmail
        password = binding.tiPassword

        setupAction()
        sendUserDataLoginRequest()
    }

    private fun setupAction() {
        binding.registerIfHavent.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun sendUserDataLoginRequest() {
        binding.btnLogin.setOnClickListener {
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                val emptyFields = getString(R.string.all_fields_must_not_be_empty)
                showSnackbar(emptyFields)
            } else {
                viewModel.loginAccount(email, password).observe(this) { response ->
                    when (response) {
                        is ResultState.Loading -> {
                            progressDialog.showLoading()
                        }
                        is ResultState.Success -> {
                            progressDialog.hideLoading()
                            val user = UserSession(
                                response.data.loginResult?.name.toString(),
                                response.data.loginResult?.token.toString(),
                                true
                            )
                            val loginSuccess = getString(R.string.login_success)
                            showSnackbar(loginSuccess)
                            viewModel.saveSession(user)
                            goToMainActivity()
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
            setBackgroundTint(ContextCompat.getColor(this@LoginActivity, R.color.black))
            setTextColor(ContextCompat.getColor(this@LoginActivity, R.color.white))
            setAction("OK") { dismiss() }
        }.show()
    }

    private fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(intent)
        finish()
    }
}