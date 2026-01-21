package com.bloom.app.ui.screens.camera


import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {
    var lastPhotoPath: String? = null
}