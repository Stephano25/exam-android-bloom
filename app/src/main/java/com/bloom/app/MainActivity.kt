package com.bloom.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bloom.app.data.local.database.AppDatabase
import com.bloom.app.data.repository.AuthRepository
import com.bloom.app.data.repository.DiscoveryRepository
import com.bloom.app.ui.navigation.AppNavGraph
import com.bloom.app.ui.theme.PlantDiscoveryJournalTheme
import com.bloom.app.ui.viewmodel.AuthState
import com.bloom.app.ui.viewmodel.AuthViewModel
import com.bloom.app.ui.viewmodel.CaptureViewModel
import com.bloom.app.ui.viewmodel.DetailViewModel
import com.bloom.app.ui.viewmodel.JournalViewModel
import com.bloom.app.ui.viewmodel.ThemeViewModel

class MainActivity : ComponentActivity() {

    private lateinit var authRepository: AuthRepository
    private lateinit var discoveryRepository: DiscoveryRepository

    private lateinit var authViewModel: AuthViewModel
    private lateinit var themeViewModel: ThemeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getDatabase(applicationContext)
        authRepository = AuthRepository()
        discoveryRepository = DiscoveryRepository(
            discoveryDao = database.discoveryDao(),
            context = applicationContext
        )

        authViewModel = AuthViewModel(authRepository)
        themeViewModel = ThemeViewModel()

        setContent {
            val isDark = themeViewModel.isDarkTheme.collectAsState().value
            val authState = authViewModel.authState.collectAsState().value

            val navController = rememberNavController()

            // Choix de l’écran de départ selon l’état d’authentification
            LaunchedEffect(authState) {
                when (authState) {
                    is AuthState.Authenticated -> {
                        // Aller vers le journal et retirer les écrans d’authentification de la backstack
                        navController.navigate("journal") {
                            popUpTo("login") { inclusive = true }
                        }
                    }
                    AuthState.Unauthenticated -> {
                        // Aller vers l’écran de login
                        navController.navigate("login") {
                            popUpTo(0)
                        }
                    }
                    is AuthState.Error -> {
                        // Ne pas quitter l’app, l’erreur est gérée dans les écrans Login/SignUp
                    }
                }
            }

            PlantDiscoveryJournalTheme(darkTheme = isDark) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavGraph(
                        navController = navController,
                        authViewModel = authViewModel,
                        themeViewModel = themeViewModel,
                        getJournalViewModel = { userId ->
                            JournalViewModel(discoveryRepository, userId)
                        },
                        getCaptureViewModel = { userId ->
                            CaptureViewModel(discoveryRepository, userId)
                        },
                        getDetailViewModel = { discoveryId ->
                            DetailViewModel(discoveryRepository, discoveryId)
                        }
                    )
                }
            }
        }
    }
}
