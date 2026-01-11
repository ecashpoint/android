package de.ecashpoint.ui.screen.login

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.ecashpoint.data.authManager.KeycloakAuthManager
import de.ecashpoint.data.models.AuthUiState
import kotlinx.coroutines.launch
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context = application.applicationContext
    val keycloakAuthManager = KeycloakAuthManager(context = context)
    var authUiState by mutableStateOf(AuthUiState())

    init {
        checkAuthStatus()
    }

    private fun checkAuthStatus() {
        viewModelScope.launch {
            authUiState = authUiState.copy(isLoading = true)

            keycloakAuthManager.loadTokens()
            if(keycloakAuthManager.isAuthenticated()){
                val token = keycloakAuthManager.getValidAccessToken()

                if(token != null){
                    authUiState = authUiState.copy(
                        isLoading = false,
                        isLoggedIn = true,
                        token = token
                    )
                }else{
                    authUiState = authUiState.copy(
                        isLoading = false,
                        isLoggedIn = false
                    )
                }
            }else{
                //no sesion guardad
                authUiState = authUiState.copy(
                    isLoading = false,
                    isLoggedIn = false
                )
            }
        }
    }

    fun login(email: String, password: String) {

        viewModelScope.launch {
            authUiState = authUiState.copy(isLoading = true , errorMessage = null)
            keycloakAuthManager.login(email = email, password = password)
                .onSuccess { token ->
                    if (token != null) {
                        authUiState = authUiState.copy(
                            isLoading = false,
                            isLoggedIn = true,
                            token = token

                        )
                    } else {
                        authUiState = authUiState.copy(
                            isLoading = false,
                            isLoggedIn = false,
                            errorMessage = "Token vacio"
                        )
                    }

                }
                .onFailure { error ->
                    authUiState = authUiState.copy(
                        isLoading = false,
                        isLoggedIn = false,
                        errorMessage = error.message
                    )
                    Log.d("loginerror" , error.message.toString())
                }
        }
    }

    fun logout(){
        viewModelScope.launch {
            keycloakAuthManager.logout()
            authUiState = authUiState.copy(
                isLoading = false,
                isLoggedIn = false,
                token = null,
                errorMessage = null
            )
        }
    }
}