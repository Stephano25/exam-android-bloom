package com.bloom.app.ui.camera

import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@Composable
fun CameraScreen(navController: NavController) {
    AndroidView(factory = { context ->
        PreviewView(context)
    })
}
