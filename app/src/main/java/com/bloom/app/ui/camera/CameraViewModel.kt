package com.bloom.app.ui.camera

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {
    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri
    fun onImageCaptured(uri: Uri) { _imageUri.value = uri }
}