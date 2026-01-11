package de.ecashpoint.data.models

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItems(
    val title:String,
    val icon: ImageVector,
    val iconOutlend: ImageVector,
    val route : String
)
