package com.health.glucoguide.ui.fragment.editprofile

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
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    fun getSession() = userRepository.getSession().asLiveData()

    fun setUserData(token: String, userData: UserSession): LiveData<ResultState<UserProfileResponse>> {
        return userRepository.setUserData(token, userData)
    }

    fun getUserData(token: String): LiveData<ResultState<UserProfileResponse>> {
        return userRepository.getUserData(token)
    }
}