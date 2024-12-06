package com.health.glucoguide.data.remote.response

data class UserSession(
    val name: String,
    val token: String? = "",
    val isLogin: Boolean = false
)
