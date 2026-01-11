package de.ecashpoint.ui.screen.ecommerce

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.ecashpoint.data.authManager.KeycloakAuthManager
import de.ecashpoint.data.models.response.UiStateEcommerce
import de.ecashpoint.data.remote.RetrofitClient
import de.ecashpoint.data.repository.EcommerceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EcommerceViewModel(application: Application): AndroidViewModel(application) {

    private val keycloakAuthManager = KeycloakAuthManager(application.applicationContext)

    private val apiService = RetrofitClient.create(keycloakAuthManager)

    private val ecommerceRepository = EcommerceRepository(apiService , keycloakAuthManager)

    private val _uiStateEcommerce = MutableStateFlow<UiStateEcommerce>(UiStateEcommerce.Loading)

    val uiStateEcommerce : StateFlow<UiStateEcommerce> = _uiStateEcommerce.asStateFlow()

    fun getAll(){
        viewModelScope.launch {
            _uiStateEcommerce.value = UiStateEcommerce.Loading
            ecommerceRepository.find()
                .onSuccess { ecommerces ->
                    _uiStateEcommerce.value = UiStateEcommerce.Success(response = ecommerces)

                }
                .onFailure { error ->
                    _uiStateEcommerce.value = UiStateEcommerce.Error(message = error)
                }
        }
    }
}