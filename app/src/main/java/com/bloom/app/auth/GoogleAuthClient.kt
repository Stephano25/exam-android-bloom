package com.bloom.app.auth

import android.content.Context
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleAuthUiClient(
    private val context: Context,
    private val auth: FirebaseAuth
) {
    private val credentialManager: CredentialManager = CredentialManager.create(context)

    suspend fun signIn(clientId: String, onResult: (Boolean) -> Unit) {
        try {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setServerClientId(clientId)
                .build()

            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            val response: GetCredentialResponse = credentialManager.getCredential(context, request)

            // ✅ Récupération correcte du token
            val googleCredential = GoogleIdTokenCredential.createFrom(response.credential.data)
            val idToken: String? = googleCredential.idToken

            if (idToken != null) {
                val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
                val result = auth.signInWithCredential(firebaseCredential).await()
                onResult(result.user != null)
            } else {
                onResult(false)
            }
        } catch (e: GetCredentialException) {
            e.printStackTrace()
            onResult(false)
        }
    }
}
