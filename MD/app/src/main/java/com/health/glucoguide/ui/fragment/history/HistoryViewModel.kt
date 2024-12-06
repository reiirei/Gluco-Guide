package com.health.glucoguide.ui.fragment.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.health.glucoguide.data.ResultState
import com.health.glucoguide.data.UserRepository
import com.health.glucoguide.data.remote.response.UserSession
import com.health.glucoguide.models.HistoryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: UserRepository
) : ViewModel() {

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private var _listHistories = MutableLiveData<List<HistoryEntity>>()
    val listHistories: LiveData<List<HistoryEntity>> get() = _listHistories

    private var _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun getSession(): LiveData<UserSession> {
        return repository.getSession().asLiveData()
    }

    fun getHistories(token: String) {
        viewModelScope.launch {
            repository.getHistories(token).observeForever{
                when (it) {
                    is ResultState.Loading -> {
                        _isLoading.value = true
                    }
                    is ResultState.Success -> {
                        _isLoading.value = false
                        _listHistories.value = it.data
                    }
                    is ResultState.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = it.error
                    }
                }
            }
        }
    }
}