package com.health.glucoguide.ui.fragment.editprofile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.health.glucoguide.data.UserRepository
import com.health.glucoguide.data.remote.request.UserInputProfile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {

    fun setUserData(token: String, userData: UserInputProfile) = userRepository.setUserData(token, userData)

    fun clearUserSession() {
        viewModelScope.launch {
            userRepository.clearSession()
        }
    }
}