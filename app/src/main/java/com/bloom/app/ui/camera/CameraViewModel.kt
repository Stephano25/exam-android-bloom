package com.bloom.app.ui.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {


    private val _photoUri = MutableStateFlow<Uri?>(null)
    val photoUri = _photoUri.asStateFlow()


    fun onPhotoCaptured(uri: Uri) {
        _photoUri.value = uri
    }
}