package com.bloom.app.history

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HistoryScreen(vm: HistoryViewModel = hiltViewModel()) {
    val plants by vm.plants.collectAsState()


    LazyColumn {
        items(plants) {
            Text(it.commonName, modifier = Modifier.padding(16.dp))
        }
    }
}