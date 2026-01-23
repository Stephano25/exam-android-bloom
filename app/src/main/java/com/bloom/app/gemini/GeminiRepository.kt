package com.bloom.app.gemini

import com.bloom.app.BuildConfig
import com.bloom.app.data.model.Plant
import com.bloom.app.utils.plantPrompt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import android.util.Base64
import javax.inject.Inject

class GeminiRepository @Inject constructor() {

    suspend fun analyzePlant(imageBytes: ByteArray): Plant =
        withContext(Dispatchers.IO) {

            val base64Image = Base64.encodeToString(imageBytes, Base64.NO_WRAP)

            val body = """
            {
              "contents": [
                {
                  "parts": [
                    { "text": "${plantPrompt()}" },
                    {
                      "inlineData": {
                        "mimeType": "image/jpeg",
                        "data": "$base64Image"
                      }
                    }
                  ]
                }
              ]
            }
            """.trimIndent()

            val url = URL(
                "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent?key=${BuildConfig.GEMINI_API_KEY}"
            )

            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "POST"
                setRequestProperty("Content-Type", "application/json")
                doOutput = true
            }

            conn.outputStream.use {
                it.write(body.toByteArray())
            }

            val response = conn.inputStream.bufferedReader().readText()

            val root = JSONObject(response)

            val textJson = root
                .getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text")

            val plantJson = JSONObject(textJson)

            Plant(
                commonName = plantJson.getString("commonName"),
                scientificName = plantJson.getString("scientificName"),
                family = plantJson.getString("family"),
                description = plantJson.getString("description"),
                waterNeeds = plantJson.getString("waterNeeds"),
                lightNeeds = plantJson.getString("lightNeeds"),
                soilType = plantJson.getString("soilType"),
                careTips = plantJson.getString("careTips"),
                diseases = plantJson.getString("diseases"),
                toxicity = plantJson.getString("toxicity")
            )
        }
}
