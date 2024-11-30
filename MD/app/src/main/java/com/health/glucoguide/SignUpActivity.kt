package com.health.glucoguide

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.health.glucoguide.databinding.ActivitySignUpBinding
import com.health.glucoguide.models.UserRegisterRequest

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
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
                showToast("All fields must not be empty")
            } else if (!email.matches(emailPattern.toRegex())) {
                showToast("Invalid email address")
            } else {
                showToast("Sign up success")
                val userRegisterRequest = UserRegisterRequest(email, username, password)
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra(userRegister, userRegisterRequest)
                startActivity(intent)
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

    companion object {
        const val userRegister = "userRegisterRequest"
    }
}