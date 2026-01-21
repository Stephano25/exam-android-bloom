package com.bloom.app.ui.camera

import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController

@Composable
fun CameraScreen(
    navController: NavController,
    viewModel: CameraViewModel = hiltViewModel
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current


    val previewView = remember { PreviewView(context) }
    val imageCapture = remember { ImageCapture.Builder().build() }


    LaunchedEffect(Unit) {
        val cameraProvider = context.getCameraProvider()
        val preview = Preview.Builder().build().also {
            it.setSurfaceProvider(previewView.surfaceProvider)
        }

        cameraProvider.unbindAll()
        cameraProvider.bindToLifecycle(
            lifecycleOwner,
            CameraSelector.DEFAULT_BACK_CAMERA,
            preview,
            imageCapture
        )
    }

    Box(Modifier.fillMaxSize()) {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize())


        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(24.dp),
            onClick = {
                val file = File(
                    context.cacheDir,
                    "plant_${System.currentTimeMillis()}.jpg"
                )
                val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

                imageCapture.takePicture(
                    outputOptions,
                    ContextCompat.getMainExecutor(context),
                    object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                            val uri = file.toUri()
                            viewModel.onPhotoCaptured(uri)
                            navController.navigate(Screen.Result.route)
                        }


                        override fun onError(exc: ImageCaptureException) {}
                    }
                )
            }
        ) {
            Icon(Icons.Default.CameraAlt, contentDescription = "Capture")
        }
    }
}