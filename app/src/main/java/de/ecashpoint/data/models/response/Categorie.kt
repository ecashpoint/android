package de.ecashpoint.data.models.response

data class Categorie(
    val id : Number,
    val category:String
)

sealed class UiStateCategory{
    object Loading: UiStateCategory()
    object Empty : UiStateCategory()
    data class Success(val categorys: List<Categorie>): UiStateCategory()
    data class Error(val message: Throwable) : UiStateCategory()
}