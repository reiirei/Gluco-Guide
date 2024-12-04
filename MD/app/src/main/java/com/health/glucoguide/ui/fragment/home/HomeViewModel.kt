package com.health.glucoguide.ui.fragment.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.data.UserRepository
import com.health.glucoguide.models.UserProfileResponse
import com.health.glucoguide.models.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getSession(): LiveData<UserSession> {
        return userRepository.getSession().asLiveData()
    }

    fun getUserData(token: String): LiveData<ResultState<UserProfileResponse>> {
        return userRepository.getUserData(token)
    }
}