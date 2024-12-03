package com.health.glucoguide.models

data class UserLoginRequest(
    val email: String,
    val password: String,
    val isLogin: Boolean = false
)
