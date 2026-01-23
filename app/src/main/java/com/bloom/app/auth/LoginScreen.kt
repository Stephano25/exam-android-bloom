package com.bloom.app.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bloom.app.navigation.Screen

@Composable
fun LoginScreen(
    navController: NavController,
    vm: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        TextField(email, { email = it }, label = { Text("Email") })
        TextField(password, { password = it }, label = { Text("Password") })
        Button(onClick = {
            vm.login(email, password) {
                if (it) navController.navigate(Screen.Camera.route)
            }
        }) { Text("Login") }
        Button(onClick = {
            // à connecter avec ActivityResultLauncher
        }) {
            Text("Continuer avec Google")
        }

    }
}

