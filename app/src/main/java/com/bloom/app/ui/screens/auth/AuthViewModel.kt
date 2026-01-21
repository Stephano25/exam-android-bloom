package com.bloom.app.ui.screens.auth


import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth
) : ViewModel() {


    fun login(email: String, password: String) =
        auth.signInWithEmailAndPassword(email, password)


    fun register(email: String, password: String) =
        auth.createUserWithEmailAndPassword(email, password)
}