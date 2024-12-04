package com.health.glucoguide.data

import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.health.glucoguide.R
import com.health.glucoguide.data.local.UserPreference
import com.health.glucoguide.data.remote.ApiService
import com.health.glucoguide.models.UserLoginRequest
import com.health.glucoguide.models.UserLoginResponse
import com.health.glucoguide.models.UserProfileResponse
import com.health.glucoguide.models.UserRegisterRequest
import com.health.glucoguide.models.UserRegisterResponse
import com.health.glucoguide.models.UserSession
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val apiService: ApiService,
    private val userPreference: UserPreference
) {

    suspend fun saveSession(user: UserSession) {
        userPreference.saveSession(user)
    }

    fun getSession() = userPreference.getSession()

    suspend fun clearSession() {
        userPreference.clearSession()
    }

    fun getUserData(token: String) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.getUserProfile("Bearer $token")
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, UserProfileResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage.toString()))
        } catch (e: IOException) {
            emit(ResultState.Error(R.string.network_connection_error.toString()))
        } catch (e: Exception) {
            emit(ResultState.Error(R.string.an_unexpected_error_occurred.toString()))
        }
    }

    fun setUserData(token: String, userData: UserSession) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.putUserProfile("Bearer $token", userData)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, UserRegisterResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage.toString()))
        } catch (e: IOException) {
            emit(ResultState.Error(R.string.network_connection_error.toString()))
        } catch (e: Exception) {
            emit(ResultState.Error(R.string.an_unexpected_error_occurred.toString()))
        }

    }

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
            emit(ResultState.Error(R.string.network_connection_error.toString()))
        } catch (e: Exception) {
            emit(ResultState.Error(R.string.an_unexpected_error_occurred.toString()))
        }
    }

    fun loginUser(request: UserLoginRequest) = liveData {
        emit(ResultState.Loading)
        try {
            val successResponse = apiService.postUserLogin(request)
            emit(ResultState.Success(successResponse))
        } catch (e: HttpException) {
            val jsonInString = e.response()?.errorBody()?.string()
            val errorBody = Gson().fromJson(jsonInString, UserLoginResponse::class.java)
            val errorMessage = errorBody.message
            emit(ResultState.Error(errorMessage.toString()))
        } catch (e: IOException) {
            emit(ResultState.Error(R.string.network_connection_error.toString()))
        } catch (e: Exception) {
            emit(ResultState.Error(R.string.an_unexpected_error_occurred.toString()))
        }
    }
}