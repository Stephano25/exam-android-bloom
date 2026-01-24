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
                composable(Screen.Login.route) { LoginScreen(navController) }
                composable(Screen.Register.route) { RegisterScreen(navController) }
                composable(Screen.Camera.route) { CameraScreen(navController) }
                composable(Screen.Result.route) { ResultScreen() }
                composable(Screen.History.route) { HistoryScreen() }
            }
        }
    }
}
