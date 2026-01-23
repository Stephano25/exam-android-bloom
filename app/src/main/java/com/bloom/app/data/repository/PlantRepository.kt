package com.bloom.app.data.repository

import com.bloom.app.data.model.Plant
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class PlantRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {
    fun savePlant(plant: Plant) {
        val uid = auth.currentUser?.uid ?: return
        firestore.collection("users").document(uid)
            .collection("plants").add(plant)
    }

    fun getPlants(): Flow<List<Plant>> = callbackFlow {
        val uid = auth.currentUser?.uid ?: return@callbackFlow
        val sub = firestore.collection("users").document(uid)
            .collection("plants")
            .addSnapshotListener { s, _ ->
                trySend(s?.toObjects(Plant::class.java) ?: emptyList())
            }
        awaitClose { sub.remove() }
    }
}
