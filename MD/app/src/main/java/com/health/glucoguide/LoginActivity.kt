package com.health.glucoguide

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.health.glucoguide.databinding.ActivityLoginBinding
import com.health.glucoguide.models.UserLoginRequest

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var username: TextInputEditText
    private lateinit var password: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        username = binding.tiUsername
        password = binding.tiPassword

        setupAction()
        sendUserDataRegisterRequest()
    }

    private fun setupAction() {
        binding.registerIfHavent.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun sendUserDataRegisterRequest() {
        binding.btnLogin.setOnClickListener {
            val username = username.text.toString().trim()
            val password = password.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                showToast("All fields must not be empty")
            } else {
                showToast("Login success")
                val userLoginRequest = UserLoginRequest(username, password)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra(userLogin, userLoginRequest)
                startActivity(intent)
            }
        }
    }

    private fun showToast(toast: String) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val userLogin = "userLoginRequest"
    }
}