package com.bloom.app.data.remote.gemini


fun plantPrompt(): String = """
Tu es un expert botaniste.
Analyse cette image de plante et retourne un JSON avec :
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