package com.health.glucoguide.ui.activity.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.health.glucoguide.databinding.ActivityFirstOnBoardingBinding
import com.health.glucoguide.ui.activity.login.LoginActivity
import com.health.glucoguide.ui.activity.signup.SignUpActivity

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.btnSignIn.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        binding.btnSignUp.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}