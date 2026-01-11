package de.ecashpoint.data.authManager

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class KeycloakAuthManager(private  val context: Context) {

    private val KEYCLOAK_URL = "http://72.61.158.67:8180"
    private val REALM = "microservices"
    private val CLIENT_ID = "movile-ecashpoint"

    private val tokenEndpoint = "$KEYCLOAK_URL/realms/$REALM/protocol/openid-connect/token"

    private var accessToken: String? = null
    private var refreshToken: String? = null
    private var tokenExpiry: Long = 0

    suspend fun login(email:String , password:String): Result<String>{
        return withContext(Dispatchers.IO) {
            try {
                val url = URL(tokenEndpoint)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                connection.doOutput = true


                // Parámetros del request
                val params = "grant_type=password" +
                        "&client_id=$CLIENT_ID" +
                        "&username=$email" +
                        "&password=$password" +
                        "&scope=openid profile email"

                // Enviar request
                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(params)
                    writer.flush()
                }

                // Leer respuesta
                val responseCode = connection.responseCode

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    val response = connection.inputStream.bufferedReader().use { it.readText() }
                    val json = JSONObject(response)

                    // Extraer tokens
                    accessToken = json.getString("access_token")
                    refreshToken = json.optString("refresh_token", null)
                    val expiresIn = json.getLong("expires_in")

                    // Calcular tiempo de expiración
                    tokenExpiry = System.currentTimeMillis() + (expiresIn * 1000)

                    // Guardar tokens
                    saveTokens()

                    Result.success(accessToken!!)
                }else {
                    val errorResponse = connection.errorStream?.bufferedReader()?.use { it.readText() }
                    val errorMsg = try {
                        val errorJson = JSONObject(errorResponse ?: "")
                        errorJson.optString("error_description", "Error de autenticación")
                    } catch (e: Exception) {
                        "Credenciales inválidas"
                    }
                    Result.failure(Exception(errorMsg))
                }
            }catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    /**
     * Refresca el access token usando el refresh token
     */
    suspend fun refreshAccessToken(): Result<String> = withContext(Dispatchers.IO) {
        try {
            if (refreshToken == null) {
                return@withContext Result.failure(Exception("No hay refresh token"))
            }

            val url = URL(tokenEndpoint)
            val connection = url.openConnection() as HttpURLConnection

            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
            connection.doOutput = true

            val params = "grant_type=refresh_token" +
                    "&client_id=$CLIENT_ID" +
                    "&refresh_token=$refreshToken"

            OutputStreamWriter(connection.outputStream).use { writer ->
                writer.write(params)
                writer.flush()
            }

            val responseCode = connection.responseCode

            if (responseCode == HttpURLConnection.HTTP_OK) {
                val response = connection.inputStream.bufferedReader().use { it.readText() }
                val json = JSONObject(response)

                accessToken = json.getString("access_token")
                refreshToken = json.optString("refresh_token", refreshToken)
                val expiresIn = json.getLong("expires_in")
                tokenExpiry = System.currentTimeMillis() + (expiresIn * 1000)

                saveTokens()

                Result.success(accessToken!!)
            } else {
                Result.failure(Exception("No se pudo refrescar el token"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Obtiene un token válido (refresca si es necesario)
     */
    suspend fun getValidAccessToken(): String? {
        // Si no hay token, retornar null
        if (accessToken == null){
            loadTokens()
        }

        if (accessToken == null) return null

        // Si el token está por expirar (menos de 1 minuto), refrescar
        if (System.currentTimeMillis() >= tokenExpiry - 60000) {
            val result = refreshAccessToken()
            return if (result.isSuccess) result.getOrNull() else null
        }

        return accessToken
    }


    /**
     * Cierra sesión
     */
    suspend fun logout(): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            if (refreshToken != null) {
                val logoutEndpoint = "$KEYCLOAK_URL/realms/$REALM/protocol/openid-connect/logout"
                val url = URL(logoutEndpoint)
                val connection = url.openConnection() as HttpURLConnection

                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                connection.doOutput = true

                val params = "client_id=$CLIENT_ID&refresh_token=$refreshToken"

                OutputStreamWriter(connection.outputStream).use { writer ->
                    writer.write(params)
                    writer.flush()
                }

                connection.responseCode // Ejecutar la petición
            }

            // Limpiar tokens locales
            accessToken = null
            refreshToken = null
            tokenExpiry = 0
            clearTokens()

            Result.success(Unit)
        } catch (e: Exception) {
            // Aunque falle el logout en servidor, limpiar local
            accessToken = null
            refreshToken = null
            tokenExpiry = 0
            clearTokens()
            Result.success(Unit)
        }
    }


    /**
     * Verifica si el usuario está autenticado
     */
    fun isAuthenticated(): Boolean {
        return accessToken != null && System.currentTimeMillis() < tokenExpiry
    }

    /**
     * Guardar tokens (usa EncryptedSharedPreferences en producción)
     */
    private fun saveTokens() {
        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        prefs.edit().apply {
            putString("access_token", accessToken)
            putString("refresh_token", refreshToken)
            putLong("token_expiry", tokenExpiry)
            apply()
        }
    }

    /**
     * Cargar tokens guardados
     */
    fun loadTokens() {
        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        accessToken = prefs.getString("access_token", null)
        refreshToken = prefs.getString("refresh_token", null)
        tokenExpiry = prefs.getLong("token_expiry", 0)
    }

    /**
     * Limpiar tokens guardados
     */
    private fun clearTokens() {
        val prefs = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}