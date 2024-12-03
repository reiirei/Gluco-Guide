package com.health.glucoguide.ui.activity.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.data.UserRepository
import com.health.glucoguide.models.UserLoginRequest
import com.health.glucoguide.models.UserLoginResponse
import com.health.glucoguide.models.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun saveSession(user: UserSession) {
        viewModelScope.launch {
            userRepository.saveSession(user)
        }
    }

    fun loginAccount(email: String, password: String): LiveData<ResultState<UserLoginResponse>> {
        val request = UserLoginRequest(email, password)

        return userRepository.loginUser(request)
    }

}