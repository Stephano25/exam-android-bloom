package com.bloom.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bloom.app.auth.LoginScreen
import com.bloom.app.auth.RegisterScreen
import com.bloom.app.history.HistoryScreen
import com.bloom.app.navigation.Screen
import com.bloom.app.result.ResultScreen
import com.bloom.app.ui.camera.CameraScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Screen.Login.route
            ) {
                // ✅ Login → Register navigation
                composable(Screen.Login.route) {
                    LoginScreen(onNavigateRegister = {
                        navController.navigate(Screen.Register.route)
                    })
                }
                composable(Screen.Register.route) {
                    RegisterScreen(onNavigateLogin = {
                        navController.navigate(Screen.Login.route)
                    })
                }

                // ✅ autres écrans
                composable(Screen.Camera.route) { CameraScreen(navController) }
                composable(Screen.Result.route) { ResultScreen() }
                composable(Screen.History.route) { HistoryScreen() }
            }
        }
    }
}
