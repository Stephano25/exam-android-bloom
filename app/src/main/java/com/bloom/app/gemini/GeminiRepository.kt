package com.bloom.app.gemini

import com.bloom.app.data.model.Plant
import javax.inject.Inject

class GeminiRepository @Inject constructor() {

    suspend fun analyzePlant(imageBytes: ByteArray): Plant {
        return Plant(
            commonName = "Monstera",
            scientificName = "Monstera deliciosa",
            family = "Araceae",
            description = "Plante tropicale décorative",
            waterNeeds = "Modéré",
            lightNeeds = "Lumière indirecte",
            soilType = "Drainé",
            careTips = "Éviter soleil direct",
            diseases = "Pourriture racinaire",
            toxicity = "Toxique pour animaux"
        )
    }
}
