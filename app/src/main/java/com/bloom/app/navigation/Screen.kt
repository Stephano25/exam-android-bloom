package com.bloom.app.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Camera : Screen("camera")
    object Result : Screen("result")
    object History : Screen("history")
}
