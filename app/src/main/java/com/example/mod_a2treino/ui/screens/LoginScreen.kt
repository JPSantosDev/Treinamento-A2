package com.example.mod_a2treino.ui.screens

import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.mod_a2treino.ui.models.AppPreferences
import com.example.mod_a2treino.ui.preferences.DataPreferences
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    context: Context
){

    val preferences = DataPreferences(context = context)

    val state by preferences.state.collectAsStateWithLifecycle(AppPreferences())

    var email by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var senhaVisivel by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var senhaValid by remember { mutableStateOf(false) }
    var emailValid by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            value = email,
            placeholder = { Text("Email", color = Color.Gray) },
            onValueChange = {email = it}
        )
        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            trailingIcon = { IconButton(onClick = {senhaVisivel = !senhaVisivel}){ Icon(Icons.Default.Visibility,contentDescription = null) } },
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
            value = senha,
            placeholder = { Text("Senha", color = Color.Gray) },
            onValueChange = {senha = it},
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                colors = CheckboxDefaults.colors(uncheckedColor = Color.Black),
                checked = state.rememberMe,
                onCheckedChange = {scope.launch { preferences.toggleRememberMe() }  }
            )
            Text("Lembrar de mim")
        }
        if (!state.firstLogin && state.rememberMe){
            Text("Pode usar biometria na próxima")
        }
        else
        Button(
            onClick = { if (!senhaValid || !emailValid ) Toast.makeText(context,"Verifique o email e senha",
                Toast.LENGTH_SHORT) else onLogin()}
        ) {Text("Acessar Sistema") }
    }


}