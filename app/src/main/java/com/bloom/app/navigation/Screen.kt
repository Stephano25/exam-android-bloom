package com.bloom.app.navigation

sealed class Screen(val route: String) {
    object Camera : Screen("camera")
    object Result : Screen("result")
    object Details : Screen("details")
}
