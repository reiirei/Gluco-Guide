package com.health.glucoguide.ui.fragment.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.health.glucoguide.R
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var _username = MutableLiveData<String>()
    val username: LiveData<String> get() = _username

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun saveLanguage(language: String) {
        viewModelScope.launch {
            userRepository.saveLanguage(language)
        }
    }

    fun getLanguage(): LiveData<String> {
        return userRepository.getLanguage()
    }

    fun getSession() = userRepository.getSession().asLiveData()

    fun getUserData(token: String) {
        viewModelScope.launch {
            userRepository.getUserData(token).observeForever {
                when (it) {
                    is ResultState.Loading -> {
                        _isLoading.value = false
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
                _errorMessage.value = context.getString(R.string.check_your_internet_connection_and_try_again)
            }
        }
    }
}