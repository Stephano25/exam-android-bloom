package com.bloom.app.utils

fun plantPrompt(): String = """
Tu es un expert botaniste.
Analyse cette image de plante et retourne STRICTEMENT un JSON valide avec :
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
"""