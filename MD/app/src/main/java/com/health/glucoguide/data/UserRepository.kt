package com.health.glucoguide.data

import android.util.Log
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.health.glucoguide.data.remote.ApiService
import com.health.glucoguide.models.UserLoginRequest
import com.health.glucoguide.models.UserLoginResponse
import com.health.glucoguide.models.UserRegisterRequest
import com.health.glucoguide.models.UserRegisterResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService
) {

    fun registerUser(request: UserRegisterRequest) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.postUserRegister(request)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, UserRegisterResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage.toString()))
        } catch (e: IOException) {
            emit(ResultState.Error("Network connection error"))
        } catch (e: Exception) {
            emit(ResultState.Error("An unexpected error occurred"))
        }
    }

    fun loginUser(request: UserLoginRequest) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.postUserLogin(request)
            Log.d("Login", successResponse.toString())
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, UserLoginResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage.toString()))
        }
    }
}