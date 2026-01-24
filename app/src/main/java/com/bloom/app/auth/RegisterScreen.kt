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
fun RegisterScreen(
    navController: NavController,
    vm: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(Modifier.padding(16.dp)) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )
        Spacer(Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") }
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = {
            vm.register(email, password) {
                if (it) navController.navigate(Screen.Camera.route)
            }
        }) {
            Text("Register")
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = { navController.navigate(Screen.Login.route) }) {
            Text("Déjà un compte ? Se connecter")
        }
    }
}
