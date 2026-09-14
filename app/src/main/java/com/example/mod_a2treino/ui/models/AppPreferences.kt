package com.example.mod_a2treino.ui.models

data class AppPreferences (
    val splashScreenSeen: Boolean = false,
    val firstLogin: Boolean = true,
    val rememberMe: Boolean = false
)