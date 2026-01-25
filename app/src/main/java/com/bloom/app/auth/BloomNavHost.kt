package com.bloom.app.auth

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BloomNavHost(navController: NavHostController) {
    NavHost(navController, startDestination = "splash") {
        composable("splash") {
            SplashScreen {
                navController.navigate("login") {
                    popUpTo("splash") { inclusive = true }
                }
            }
        }
        composable("login") {
            LoginScreen(onNavigateRegister = {
                navController.navigate("register")
            })
        }
        composable("register") {
            RegisterScreen(onNavigateLogin = {
                navController.navigate("login")
            })
        }
    }
}
