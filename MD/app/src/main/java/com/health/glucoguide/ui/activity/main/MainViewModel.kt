package com.health.glucoguide.ui.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.health.glucoguide.data.UserRepository
import com.health.glucoguide.data.local.UserPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userPreference: UserPreference,
) : ViewModel() {

    fun getSession() = userRepository.getSession().asLiveData()

    fun getLanguageSync(): String {
        return userPreference.getLanguageSync()
    }

}