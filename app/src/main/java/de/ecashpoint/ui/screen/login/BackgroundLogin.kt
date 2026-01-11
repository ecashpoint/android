package de.ecashpoint.ui.screen.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import de.ecashpoint.R
@Composable
fun BackGroundLogin(
    content: @Composable ()-> Unit
){

    Box(modifier = Modifier.fillMaxSize().background(
        brush = Brush.linearGradient (
            colors = listOf(
                Color(0xFFF9D670),
                Color(0xFFD4EFF6),
                Color(0xFF96C97C),


            )
        )
    )) {
//moneda principal
        Image(
            painter = painterResource(id = R.drawable.moneda1), // Cambia por tu imagen
            contentDescription = "Decoración superior",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.TopStart)
                .offset(x = (-40).dp, y = (50).dp)
                .clip(CircleShape), // Hace la imagen circular
            contentScale = ContentScale.Crop
        )

        Image(
            painter = painterResource(id = R.drawable.moneda2), // Cambia por tu imagen
            contentDescription = "Decoración superior",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.BottomEnd)
                .offset(x = (20).dp, y = (-50).dp)
                .clip(CircleShape), // Hace la imagen circular
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            content()
        }
    }
}