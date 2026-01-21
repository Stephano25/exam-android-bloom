package com.bloom.app.data.repository


import com.bloom.app.data.model.Plant
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject


class PlantRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun savePlant(userId: String, plant: Plant) {
        firestore
            .collection("users")
            .document(userId)
            .collection("plants")
            .add(plant)
    }
}