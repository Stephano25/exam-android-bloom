package com.bloom.app.gemini

import android.util.Base64
import com.bloom.app.BuildConfig
import com.bloom.app.data.model.Plant
import com.bloom.app.utils.plantPrompt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class GeminiRepository @Inject constructor() {

    suspend fun analyzePlant(imageBytes: ByteArray): Plant? =
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

            return@withContext try {
                conn.outputStream.use { it.write(body.toByteArray()) }

                val response = conn.inputStream.bufferedReader().readText()
                conn.disconnect()

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
                    commonName = plantJson.optString("commonName", ""),
                    scientificName = plantJson.optString("scientificName", ""),
                    family = plantJson.optString("family", ""),
                    description = plantJson.optString("description", ""),
                    waterNeeds = plantJson.optString("waterNeeds", ""),
                    lightNeeds = plantJson.optString("lightNeeds", ""),
                    soilType = plantJson.optString("soilType", ""),
                    careTips = plantJson.optString("careTips", ""),
                    diseases = plantJson.optString("diseases", ""),
                    toxicity = plantJson.optString("toxicity", "")
                )
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
}
