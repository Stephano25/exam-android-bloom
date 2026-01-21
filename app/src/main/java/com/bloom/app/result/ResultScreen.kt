package com.bloom.app.result

import androidx.compose.runtime.Composable
import com.bloom.app.ui.camera.CameraViewModel

@Composable
fun ResultScreen(
    cameraViewModel: CameraViewModel = hiltViewModel()
) {
    val uri by cameraViewModel.photoUri.collectAsState()


    uri?.let {
        Image(
            painter = rememberAsyncImagePainter(it),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}