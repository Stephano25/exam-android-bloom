package com.bloom.app.gemini

fun plantPrompt(): String = """
Tu es un expert botaniste.
Analyse cette image et retourne STRICTEMENT un JSON valide avec :
- commonName
- scientificName
- family
- description
- waterNeeds
- lightNeeds
- soilType
- careTips
- diseases
- toxicity
""".trimIndent()
