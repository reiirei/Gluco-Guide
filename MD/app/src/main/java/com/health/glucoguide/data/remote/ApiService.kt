package com.health.glucoguide.data.remote

import com.health.glucoguide.data.remote.response.UserHistoriesResponse
import com.health.glucoguide.data.remote.request.UserInputProfile
import com.health.glucoguide.data.remote.request.UserLoginRequest
import com.health.glucoguide.data.remote.response.UserLoginResponse
import com.health.glucoguide.data.remote.response.UserProfileResponse
import com.health.glucoguide.data.remote.request.UserRegisterRequest
import com.health.glucoguide.data.remote.response.UserRegisterResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @POST("auth/register")
    suspend fun postUserRegister(
        @Body request: UserRegisterRequest
    ): UserRegisterResponse

    @POST("auth/login")
    suspend fun postUserLogin(
    @Body request: UserLoginRequest
    ): UserLoginResponse

    @PUT("profile")
    suspend fun putUserProfile(
        @Header("Authorization") token: String,
        @Body userData: UserInputProfile
    ): UserProfileResponse

    @GET("profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): UserProfileResponse

    @GET("histories")
    suspend fun getUserHistories(
        @Header("Authorization") token: String
    ): UserHistoriesResponse
}
