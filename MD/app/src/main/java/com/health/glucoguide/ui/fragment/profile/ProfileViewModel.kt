package com.health.glucoguide.ui.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.data.UserRepository
import com.health.glucoguide.models.UserProfileResponse
import com.health.glucoguide.models.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getSession(): LiveData<UserSession> {
        return userRepository.getSession().asLiveData()
    }

    fun getUserData(token: String): LiveData<ResultState<UserProfileResponse>> {
        return userRepository.getUserData(token)
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.clearSession()
        }
    }
}