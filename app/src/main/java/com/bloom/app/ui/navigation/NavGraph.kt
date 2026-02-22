package com.bloom.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bloom.app.ui.screens.auth.LoginScreen
import com.bloom.app.ui.screens.auth.SignUpScreen
import com.bloom.app.ui.screens.capture.CaptureScreen
import com.bloom.app.ui.screens.detail.DetailScreen
import com.bloom.app.ui.screens.journal.JournalScreen
import com.bloom.app.ui.viewmodel.AuthState
import com.bloom.app.ui.viewmodel.AuthViewModel
import com.bloom.app.ui.viewmodel.CaptureViewModel
import com.bloom.app.ui.viewmodel.DetailViewModel
import com.bloom.app.ui.viewmodel.JournalViewModel
import com.bloom.app.ui.viewmodel.ThemeViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object SignUp : Screen("signup")
    object Journal : Screen("journal")
    object Capture : Screen("capture")
    object Detail : Screen("detail/{discoveryId}") {
        fun createRoute(discoveryId: Long) = "detail/$discoveryId"
    }
}

@Composable
fun AppNavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    themeViewModel: ThemeViewModel,
    getJournalViewModel: (String) -> JournalViewModel,
    getCaptureViewModel: (String) -> CaptureViewModel,
    getDetailViewModel: (Long) -> DetailViewModel
) {
    val authState by authViewModel.authState.collectAsState()

    // État global pour la taille du texte (Paramètres)
    var textSize by remember { mutableStateOf("Moyen") } // "Petit", "Moyen", "Grand"

    val startDestination = when (authState) {
        is AuthState.Authenticated -> Screen.Journal.route
        else -> Screen.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onAuthSuccess = {
                    navController.navigate(Screen.Journal.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToSignUp = {
                    navController.navigate(Screen.SignUp.route)
                }
            )
        }

        composable(Screen.SignUp.route) {
            SignUpScreen(
                viewModel = authViewModel,
                onAuthSuccess = {
                    navController.navigate(Screen.Journal.route) {
                        popUpTo(Screen.SignUp.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Journal.route) {
            val userId = (authState as? AuthState.Authenticated)?.userId ?: return@composable
            val viewModel = getJournalViewModel(userId)

            JournalScreen(
                viewModel = viewModel,
                onNavigateToCapture = {
                    navController.navigate(Screen.Capture.route)
                },
                onNavigateToDetail = { discoveryId ->
                    navController.navigate(Screen.Detail.createRoute(discoveryId))
                },
                onSignOut = {
                    authViewModel.signOut()
                    navController.navigate(Screen.Login.route) {
                        popUpTo(0) { inclusive = true }
                    }
                },
                themeViewModel = themeViewModel,
                textSize = textSize,
                onTextSizeChange = { newSize -> textSize = newSize }
            )
        }

        composable(Screen.Capture.route) {
            val userId = (authState as? AuthState.Authenticated)?.userId ?: return@composable
            val viewModel = getCaptureViewModel(userId)

            CaptureScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToDetail = { discoveryId ->
                    navController.navigate(Screen.Detail.createRoute(discoveryId)) {
                        popUpTo(Screen.Journal.route)
                    }
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("discoveryId") { type = NavType.LongType }
            )
        ) { backStackEntry ->
            val discoveryId = backStackEntry.arguments?.getLong("discoveryId") ?: return@composable
            val viewModel = getDetailViewModel(discoveryId)

            DetailScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                textSize = textSize
            )
        }
    }
}
