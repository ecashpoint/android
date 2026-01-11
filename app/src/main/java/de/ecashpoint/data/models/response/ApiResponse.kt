package de.ecashpoint.data.models.response

data class ApiResponse<T>(
    val success : Boolean,
    val data: T?,
    val message: String?
)
