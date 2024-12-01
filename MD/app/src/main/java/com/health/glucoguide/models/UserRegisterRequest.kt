package com.health.glucoguide.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRegisterRequest(
    val email: String,
    val name: String,
    val password: String
) : Parcelable
