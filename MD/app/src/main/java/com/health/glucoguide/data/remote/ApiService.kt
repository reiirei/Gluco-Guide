package com.health.glucoguide.data.remote

import com.health.glucoguide.models.UserLoginRequest
import com.health.glucoguide.models.UserLoginResponse
import com.health.glucoguide.models.UserProfileResponse
import com.health.glucoguide.models.UserRegisterRequest
import com.health.glucoguide.models.UserRegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("app/register")
    suspend fun postUserRegister(
        @Body request: UserRegisterRequest
    ): UserRegisterResponse

    @POST("app/login")
    suspend fun postUserLogin(
    @Body request: UserLoginRequest
    ): UserLoginResponse

    @GET("app/profile")
    suspend fun getUserProfile(): UserProfileResponse
}
