package com.health.glucoguide.ui.activity.splashscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.health.glucoguide.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {
    fun getSession() = repository.getSession().asLiveData()
}