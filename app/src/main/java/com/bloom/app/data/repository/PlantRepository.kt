package com.bloom.app.data.repository

class PlantRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun savePlant(userId: String, plant: Plant) {
        firestore.collection("users")
            .document(userId)
            .collection("plants")
            .add(plant)
    }
}