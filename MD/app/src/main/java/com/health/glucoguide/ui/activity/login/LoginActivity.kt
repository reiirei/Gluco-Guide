package com.health.glucoguide.ui.activity.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.health.glucoguide.util.ProgressDialogUtil
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.databinding.ActivityLoginBinding
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
                showToast("All fields must not be empty")
            } else {
                viewModel.loginAccount(email, password).observe(this) { response ->
                    when (response) {
                        is ResultState.Loading -> {
                            progressDialog.showLoading()
                        }
                        is ResultState.Success -> {
                            progressDialog.hideLoading()
                            showToast("Login success")
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        is ResultState.Error -> {
                            progressDialog.hideLoading()
                            showToast("Login failed: ${response.error}")
                        }
                    }
                }
            }
        }
    }

    private fun showToast(toast: String) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }
}