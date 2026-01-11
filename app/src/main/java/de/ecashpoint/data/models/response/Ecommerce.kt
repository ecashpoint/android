package de.ecashpoint.data.models.response

import android.widget.TableRow

data class Ecommerce(
    val id : Number,
    val authId:String,
    val ecommerceName:String,
    val ecommerceAddress:String,
    val ecommerceNit:String,
    val ecommerceDv:String,
    val ecommercePercentage: Double,
    val ecommercePhone:String,
    val ecommerceEmail:String,
    val ecommerceWhatsapp:String,
    val ecommerceStatus: Boolean,
    val categories : List<Categorie>,
    val redes: List<Redes>,
    val assets: List<Assets>,
    val createdAt:String,
    val updatedAt:String
)

data class Ecommerces(
    val totalCount:Number,
    val ecommerces: List<Ecommerce>,
    val totalPages:Number,
    val page:Number,
    val size :Number
)

data class Assets(
    val publicId:String,
    val secureUrl:String,
    val url:String,
    val format:String,
    val width:Number,
    val height:Number,
    val type:String,
    val status:String
)

enum class TypeImagen {
    BANNER,
    THUMBNAIL

}

sealed class UiStateEcommerce{
    object Loading: UiStateEcommerce()
    object Empty: UiStateEcommerce()
    data class Success(var response: Ecommerces): UiStateEcommerce()
    data class Error(var message: Throwable): UiStateEcommerce()
}