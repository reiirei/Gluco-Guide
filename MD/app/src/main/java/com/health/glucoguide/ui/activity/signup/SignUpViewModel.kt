package com.health.glucoguide.ui.activity.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.data.UserRepository
import com.health.glucoguide.models.UserRegisterRequest
import com.health.glucoguide.models.UserRegisterResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun registerAccount(name: String, email: String, password: String): LiveData<ResultState<UserRegisterResponse>> {
        val request = UserRegisterRequest(name, email, password)

        return userRepository.registerUser(request)
    }
}