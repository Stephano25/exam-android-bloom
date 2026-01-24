package com.bloom.app.result

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bloom.app.ui.camera.CameraViewModel
import com.bloom.app.data.model.Plant
import androidx.compose.ui.Alignment

@Composable
fun ResultScreen(
    cameraViewModel: CameraViewModel = hiltViewModel(),
    resultViewModel: ResultViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uri by cameraViewModel.photoUri.collectAsState()
    val plant by resultViewModel.plant.collectAsState()

    // ⚡ Lancer l'analyse quand une photo est disponible
    LaunchedEffect(uri) {
        uri?.let {
            context.contentResolver.openInputStream(it)?.readBytes()?.let { bytes ->
                resultViewModel.analyze(bytes)
            }
        }
    }

    when {
        plant != null -> {
            plant?.let { analyzedPlant: Plant ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    shape = RoundedCornerShape(20.dp),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            analyzedPlant.commonName,
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Text(
                            analyzedPlant.scientificName,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(analyzedPlant.description)
                    }
                }
            }
        }
        uri != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Aucune analyse disponible")
                CircularProgressIndicator()
            }
        }
    }
}
