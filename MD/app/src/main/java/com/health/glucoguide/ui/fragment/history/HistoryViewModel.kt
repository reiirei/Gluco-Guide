package com.health.glucoguide.ui.fragment.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.health.glucoguide.data.UserRepository
import com.health.glucoguide.models.UserSession
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    fun getSession(): LiveData<UserSession> {
        return repository.getSession().asLiveData()
    }

    fun getHistories(token: String) = repository.getHistories(token)
}