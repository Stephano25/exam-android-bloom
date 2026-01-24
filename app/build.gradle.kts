plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.bloom.app"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.bloom.app"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        // ✅ Récupération depuis local.properties
        buildConfigField(
            "String",
            "GEMINI_API_KEY",
            "\"${project.findProperty("GEMINI_API_KEY") ?: ""}\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        buildConfig = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // =============================
    // Core & Lifecycle
    // =============================
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")

    // =============================
    // Compose BOM (STABLE)
    // =============================
    implementation(platform("androidx.compose:compose-bom:2024.02.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // =============================
    // Hilt
    // =============================
    implementation("com.google.dagger:hilt-android:2.50")
    kapt("com.google.dagger:hilt-compiler:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    implementation("com.google.guava:guava:31.1-android")
    // =============================
    // Firebase (BOM OFFICIEL)
    // =============================
    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-firestore")

    // =============================
    // Google Sign-In
    // =============================
    implementation("com.google.android.gms:play-services-auth:21.2.0")

    // =============================
    // CameraX
    // =============================
    implementation("androidx.camera:camera-camera2:1.3.4")
    implementation("androidx.camera:camera-lifecycle:1.3.4")
    implementation("androidx.camera:camera-view:1.3.4")

    // =============================
    // Coroutines
    // =============================
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.8.1")

    // =============================
    // Material (XML / compat)
    // =============================
    implementation("com.google.android.material:material:1.12.0")
}
