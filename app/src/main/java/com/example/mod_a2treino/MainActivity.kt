package com.example.mod_a2treino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mod_a2treino.ui.enums.CurrentScreen
import com.example.mod_a2treino.ui.models.AppPreferences
import com.example.mod_a2treino.ui.preferences.DataPreferences
import com.example.mod_a2treino.ui.screens.LoginScreen
import com.example.mod_a2treino.ui.screens.SplashScreen
import com.example.mod_a2treino.ui.theme.MOD_A2TreinoTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MOD_A2TreinoTheme {
               AppScreens()
            }
        }
    }
}

@Composable
fun AppScreens(){

    var currentScreen by remember { mutableStateOf(CurrentScreen.LOGIN) }
    val context = LocalContext.current
    val preferences = DataPreferences(context)
    val state by preferences.state.collectAsStateWithLifecycle(AppPreferences())
    val scope = rememberCoroutineScope()


    when(currentScreen){
        CurrentScreen.LOGIN -> {LoginScreen(onLogin = { scope.launch { preferences.toggleFirstLogin() } }, context = context )}
        CurrentScreen.SPLASHSCREEN -> { SplashScreen() }
    }

}