package de.ecashpoint.data.models


data class AuthUiState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val errorMessage:String ? = null,
    val token: String ? = null
)