package de.ecashpoint.data.remote.interceptor

import android.util.Log
import de.ecashpoint.data.authManager.KeycloakAuthManager
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val keycloakAuthManager: KeycloakAuthManager): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val token = runBlocking {
            keycloakAuthManager.getValidAccessToken()
        }
        Log.d("RepositoryCategory" , "$token")
        val request = if(token != null){
            Log.d("RepositoryCategory" , token)
            chain.request().newBuilder()
                .addHeader("Authorization" , "Bearer $token")
                .build()
        }else{
            chain.request()
        }

        return chain.proceed(request)
    }

}