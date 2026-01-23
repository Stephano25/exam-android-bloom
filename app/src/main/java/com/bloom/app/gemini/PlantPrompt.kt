package com.bloom.app.gemini

fun plantPrompt() = """
Tu es un expert botaniste.
Analyse cette image et retourne STRICTEMENT un JSON avec :
commonName, scientificName, family, description,
waterNeeds, lightNeeds, soilType, careTips, diseases, toxicity
""".trimIndent()

