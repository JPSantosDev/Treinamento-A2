package com.example.mod_a2treino.ui.preferences

import android.content.Context
import androidx.compose.runtime.remember
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.example.mod_a2treino.ui.models.AppPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore("app_preferences")

class DataPreferences (private val context: Context) {

    object Keys{
        val SPLASHSCREEN_SEEN = booleanPreferencesKey("splashscreen_seen")
        val FIRST_LOGIN = booleanPreferencesKey("first_login")
        val REMEMBER_ME = booleanPreferencesKey("remember_me")
    }

    val state: Flow<AppPreferences> = context.dataStore.data.map { preferences ->
        AppPreferences(
            splashScreenSeen = preferences[Keys.SPLASHSCREEN_SEEN] ?: false,
            firstLogin = preferences[Keys.FIRST_LOGIN] ?: true,
            rememberMe = preferences[Keys.REMEMBER_ME] ?: false
        )
    }

    suspend fun toggleFirstLogin(){
        context.dataStore.edit { preferences ->
            val atual = preferences[Keys.FIRST_LOGIN] ?: true
            preferences[Keys.FIRST_LOGIN] = !atual
        }
    }
    suspend fun toggleSplashScreen(){
        context.dataStore.edit { preferences ->
            val atual = preferences[Keys.SPLASHSCREEN_SEEN] ?: false
            preferences[Keys.SPLASHSCREEN_SEEN] = !atual
        }
    }
    suspend fun toggleRememberMe(){
        context.dataStore.edit { preferences ->
            val atual = preferences[Keys.REMEMBER_ME] ?: false
            preferences[Keys.REMEMBER_ME] = !atual
        }
    }

}