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

    fun validarEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS .matcher(email) .matches()
    }
    fun validarSenha(senha: String): Boolean {
        val temNumero = senha.any{it.isDigit()}
        val temLetra = senha.any{it.isLetter()}
        return senha.length >= 6 && temLetra && temNumero
    }

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
            onValueChange = {
                email = it
                emailValid = validarEmail(it)

            },
            isError = !emailValid,
            supportingText = {
                if (email.isBlank()) Text("O email é obrigatorio")
                else if (!emailValid) Text("Digite um email válido")
            }
        )
        OutlinedTextField(
            modifier = Modifier.padding(8.dp),
            trailingIcon = { IconButton(onClick = {senhaVisivel = !senhaVisivel}){ Icon(Icons.Default.Visibility,contentDescription = null) } },
            visualTransformation = if (senhaVisivel) VisualTransformation.None else PasswordVisualTransformation(),
            value = senha,
            placeholder = { Text("Senha", color = Color.Gray) },
            onValueChange = {
                senha = it
                senhaValid = validarSenha(it)
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            isError = !senhaValid,
            supportingText = {
                if (senha.isBlank()) Text("A senha é obrigatória")
                else if (senha.length < 6) Text("A senha deve conter pelo menos 6 caracteres")
                else if (!senha.any{it.isDigit()}) Text("A senha deve conter pelo menos um número")
                else if (!senha.any { it.isLetter() }) Text("A senha deve conter pelo menos uma letra")
            }
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
                onClick = {
                    if (senhaValid == false) Toast.makeText(context,"Verifique sua senha", Toast.LENGTH_SHORT).show()
                    else if (emailValid) Toast.makeText(context,"Verifique seu email", Toast.LENGTH_SHORT).show()
                    else onLogin()
                }
            ) {Text("Acessar Sistema") }
    }
}