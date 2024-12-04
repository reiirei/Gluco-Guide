package com.health.glucoguide.data.remote

import com.health.glucoguide.models.UserLoginRequest
import com.health.glucoguide.models.UserLoginResponse
import com.health.glucoguide.models.UserProfileResponse
import com.health.glucoguide.models.UserRegisterRequest
import com.health.glucoguide.models.UserRegisterResponse
import com.health.glucoguide.models.UserSession
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @POST("app/register")
    suspend fun postUserRegister(
        @Body request: UserRegisterRequest
    ): UserRegisterResponse

    @POST("app/login")
    suspend fun postUserLogin(
    @Body request: UserLoginRequest
    ): UserLoginResponse

    @PUT("app/profile")
    suspend fun putUserProfile(
        @Header("Authorization") token: String,
        @Body userData: UserSession
    ): UserProfileResponse

    @GET("app/profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): UserProfileResponse
}
