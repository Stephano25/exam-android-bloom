package com.bloom.app.result

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bloom.app.data.model.Plant
import com.bloom.app.data.repository.PlantRepository
import com.bloom.app.gemini.GeminiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResultViewModel @Inject constructor(
    private val geminiRepository: GeminiRepository,
    private val plantRepository: PlantRepository
) : ViewModel() {

    private val _plant = MutableStateFlow<Plant?>(null)
    val plant = _plant.asStateFlow()

    fun analyze(imageBytes: ByteArray) {
        viewModelScope.launch {
            val result = geminiRepository.analyzePlant(imageBytes)
            _plant.value = result
            plantRepository.savePlant(result)
        }
    }
}
