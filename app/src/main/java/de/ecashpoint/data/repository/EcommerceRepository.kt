package de.ecashpoint.data.repository

import android.util.Log
import androidx.compose.ui.platform.isDebugInspectorInfoEnabled
import de.ecashpoint.data.authManager.KeycloakAuthManager
import de.ecashpoint.data.models.response.Ecommerces
import de.ecashpoint.data.remote.api.ApiService

class EcommerceRepository(
    private val apiService: ApiService,
    private val keycloakAuthManager: KeycloakAuthManager
) {
    suspend fun find(): Result<Ecommerces>{
        Log.d("EcommerceRepository" , "find() fue llamado")
        return try {
            val response = apiService.getEcommerceAll()
            Log.d("EcommerceRepository", "📡 API response raw: $response")
            if(response.isSuccessful && response.body() != null){
                Log.d("EcommerceRepository" , "$response")
                Result.success(response.body()!!)

            }else{
                val errorMg = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(Exception(errorMg))
            }
        }catch (e: Exception){
            Log.e("EcommerceRepository", "💥 Excepción en getAll(): ${e.message}", e)
            Result.failure(e)
        }
    }
}