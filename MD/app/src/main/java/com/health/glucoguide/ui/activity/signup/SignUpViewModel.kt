package com.health.glucoguide.ui.activity.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.data.UserRepository
import com.health.glucoguide.data.local.UserPreference
import com.health.glucoguide.data.remote.request.UserRegisterRequest
import com.health.glucoguide.data.remote.response.UserRegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreference: UserPreference
) : ViewModel() {

    fun registerAccount(name: String, email: String, password: String): LiveData<ResultState<UserRegisterResponse>> {
        val request = UserRegisterRequest(name, email, password)

        return userRepository.registerUser(request)
    }

    fun getLanguageSync(): String {
        return userPreference.getLanguageSync()
    }
}