package com.bloom.app

import android.app.Application
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BloomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // ✅ Initialisation Firebase
        FirebaseApp.initializeApp(this)

        // ✅ Initialisation Analytics
        val analytics: FirebaseAnalytics = Firebase.analytics
        analytics.logEvent(FirebaseAnalytics.Event.APP_OPEN, null)
    }
}
