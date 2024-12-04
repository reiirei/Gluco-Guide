package com.health.glucoguide.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.health.glucoguide.models.UserSession
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreference @Inject constructor(@ApplicationContext context: Context) {

    private val dataStore: DataStore<Preferences> = context.dataStore

    suspend fun saveSession(user: UserSession) {
        dataStore.edit { preferences ->
            preferences[USER_EMAIL] = user.email
            preferences[USER_NAME] = user.name
            preferences[USER_PASSWORD] = user.password
            preferences[USER_TOKEN] = user.token ?: ""
            preferences[USER_IS_LOGIN] = user.isLogin
        }
    }

    fun getSession(): Flow<UserSession> {
        return dataStore.data.map { preferences ->
            UserSession(
                preferences[USER_EMAIL] ?: "",
                preferences[USER_NAME] ?: "",
                preferences[USER_PASSWORD] ?: "",
                preferences[USER_TOKEN] ?: "",
                preferences[USER_IS_LOGIN] ?: false
            )
        }
    }

    suspend fun clearSession() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        private val USER_EMAIL = stringPreferencesKey("email")
        private val USER_NAME = stringPreferencesKey("name")
        private val USER_PASSWORD = stringPreferencesKey("password")
        private val USER_TOKEN = stringPreferencesKey("token")
        private val USER_IS_LOGIN = booleanPreferencesKey("isLogin")
    }
}

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")