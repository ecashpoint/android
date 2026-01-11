package de.ecashpoint.data.repository

import android.util.Log
import de.ecashpoint.data.authManager.KeycloakAuthManager
import de.ecashpoint.data.models.response.Categorie
import de.ecashpoint.data.remote.api.ApiService
import de.ecashpoint.ui.screen.login.Login

class CategoryRepository(
    private val apiService: ApiService,
    private val keycloakAuthManager: KeycloakAuthManager
) {

    suspend fun getAll(): Result<List<Categorie>>{
        Log.d("RespositoryCategory", "getAll() fue llamado")
        return try {
            val response = apiService.getCategoryAll()
            Log.d("RepositoryCategory", "📡 API response raw: $response")
            if(response.isSuccessful && response.body() != null){
                Log.d("RespositoryCategory" , "$response")
                Result.success(response.body()!!)

            }else{
                val errorMg = response.errorBody()?.string() ?: "Error desconocido"
                Result.failure(Exception(errorMg))
            }
        }catch (e: Exception){
            Log.e("RepositoryCategory", "💥 Excepción en getAll(): ${e.message}", e)
            Result.failure(e)
        }
    }
}