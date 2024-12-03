package com.health.glucoguide.data.remote

import com.health.glucoguide.models.UserLoginRequest
import com.health.glucoguide.models.UserLoginResponse
import com.health.glucoguide.models.UserRegisterRequest
import com.health.glucoguide.models.UserRegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("api/register")
    suspend fun postUserRegister(
        @Body request: UserRegisterRequest
    ): UserRegisterResponse

    @POST("api/login")
    suspend fun postUserLogin(
    @Body request: UserLoginRequest
    ): UserLoginResponse
}
