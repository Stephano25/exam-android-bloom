package com.bloom.app.result

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bloom.app.ui.camera.CameraViewModel

@Composable
fun ResultScreen(
    cameraViewModel: CameraViewModel = hiltViewModel(),
    resultViewModel: ResultViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uri by cameraViewModel.photoUri.collectAsState()
    val plant by resultViewModel.plant.collectAsState()

    LaunchedEffect(uri) {
        uri?.let {
            val bytes = context.contentResolver
                .openInputStream(it)
                ?.readBytes()

            bytes?.let { image ->
                resultViewModel.analyze(image)
            }
        }
    }

    plant?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = it.commonName,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(text = it.scientificName)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = it.description)
        }
    }
}
