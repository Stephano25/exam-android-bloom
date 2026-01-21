package com.bloom.app.utils

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.getCameraProvider(): ProcessCameraProvider =
    suspendCoroutine { continuation ->
        val provider = ProcessCameraProvider.getInstance(this)
        provider.addListener(
            { continuation.resume(provider.get()) },
            ContextCompat.getMainExecutor(this)
        )
    }