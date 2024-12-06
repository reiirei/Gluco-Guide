package com.health.glucoguide.ui.fragment.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.data.UserRepository
import com.health.glucoguide.data.remote.response.UserProfileResponse
import com.health.glucoguide.data.remote.response.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getSession(): LiveData<UserSession> {
        return userRepository.getSession().asLiveData()
    }

    fun getUserData(token: String) {
        viewModelScope.launch {
            userRepository.getUserData(token).observeForever {
                when (it) {
                    is ResultState.Loading -> {
                        _isLoading.value = true
                    }
                    is ResultState.Success -> {
                        _isLoading.value = false
                        _username.value = it.data.name ?: ""
                    }
                    is ResultState.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = it.error
                    }
                }
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                userRepository.clearSession()
            } catch (e: ConnectException) {
                _errorMessage.value = "Failed to connect. Please check your internet connection."
            }
        }
    }
}