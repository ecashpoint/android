package de.ecashpoint.data.remote

import android.content.Context
import de.ecashpoint.data.authManager.KeycloakAuthManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class EcommerceApi(private val context: Context) {

    val keycloakAuthManager  = KeycloakAuthManager(context = context)

    /**
     * Realiza una petición POST al Kong Gateway
     */
    suspend fun callKongAPIPOST(endpoint: String, jsonBody: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val token = keycloakAuthManager.getValidAccessToken()
            if (token == null) {
                return@withContext Result.failure(Exception("No hay token válido"))
            }

            val url = URL(endpoint)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "POST"
            connection.setRequestProperty("Authorization", "Bearer $token")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.doOutput = true

            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(jsonBody)
                writer.flush()
            }

            val responseCode = connection.responseCode

            if (responseCode in 200..299) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                Result.success(response)
            } else {
                val error = connection.errorStream?.bufferedReader()?.use { it.readText() }
                Result.failure(Exception("Error $responseCode: $error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    /**
     * Realiza una petición al Kong Gateway con el token
     */
    suspend fun callKongAPI(endpoint: String): Result<String> = withContext(Dispatchers.IO) {
        try {
            val token = keycloakAuthManager.getValidAccessToken()
            if (token == null) {
                return@withContext Result.failure(Exception("No hay token válido"))
            }

            val url = URL(endpoint)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "GET"
            connection.setRequestProperty("Authorization", "Bearer $token")
            connection.setRequestProperty("Content-Type", "application/json")

            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                Result.success(response)
            } else {
                val error = connection.errorStream?.bufferedReader()?.use { it.readText() }
                Result.failure(Exception("Error $responseCode: $error"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}