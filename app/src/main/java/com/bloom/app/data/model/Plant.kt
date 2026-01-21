package com.bloom.app.data.model

data class Plant(
    val id: String = "",
    val commonName: String = "",
    val scientificName: String = "",
    val family: String = "",
    val description: String = "",
    val waterNeeds: String = "",
    val lightNeeds: String = "",
    val soilType: String = "",
    val careTips: String = "",
    val diseases: String = "",
    val toxicity: String = "",
    val imageUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)