package de.ecashpoint.data.remote.api

import de.ecashpoint.data.models.response.Categorie
import de.ecashpoint.data.models.response.Ecommerces
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //category
    @GET("/api/categories")
    suspend fun getCategoryAll(): Response<List<Categorie>>

    //ecommerce
    @GET("/api/ecommerces/find")
    suspend fun getEcommerceAll(
        @Query("page") page: Int? = null,
        @Query("size") size:Int? = null,
        @Query("status")status: Boolean? = null,
        @Query("authId")authId:String?=null,
        @Query("name")name:String?=null,
        @Query("email")email:String?=null
    ): Response<Ecommerces>
}