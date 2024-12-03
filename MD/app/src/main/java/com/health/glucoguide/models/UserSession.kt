package com.health.glucoguide.models

data class UserSession(
    val email: String,
    val name: String,
    val token: String,
    val isLogin: Boolean = false
)
