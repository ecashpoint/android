package de.ecashpoint.ui.screen.category

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import de.ecashpoint.data.authManager.KeycloakAuthManager
import de.ecashpoint.data.models.response.UiStateCategory
import de.ecashpoint.data.remote.RetrofitClient
import de.ecashpoint.data.repository.CategoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class CategoryViewModel(application: Application): AndroidViewModel(application) {

    private val keycloakAuthManager = KeycloakAuthManager(application.applicationContext)

    private val apiService = RetrofitClient.create(keycloakAuthManager)

    private val categoryRepository = CategoryRepository(apiService , keycloakAuthManager)

    private val _uiStateCategory = MutableStateFlow<UiStateCategory>(UiStateCategory.Loading)

    val uiStateCategory: StateFlow<UiStateCategory> = _uiStateCategory.asStateFlow()

    fun getCategories(){
        viewModelScope.launch {
            _uiStateCategory.value = UiStateCategory.Loading
            categoryRepository.getAll()
                .onSuccess { resp ->
                    _uiStateCategory.value = UiStateCategory.Success(resp)
                }
                .onFailure { error ->
                    _uiStateCategory.value = UiStateCategory.Error(error)
                }
        }
    }


}