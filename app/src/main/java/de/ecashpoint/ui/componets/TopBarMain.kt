package de.ecashpoint.ui.componets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.ecashpoint.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMain(){

    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id= R.drawable.logo),
                    contentDescription = "Logo ecashpoint",
                    modifier = Modifier.size(60.dp)
                )
                Spacer(Modifier.width(16.dp))
                Text(
                    text = "E-SMARTPOINTS",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }


        },
        actions = {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Right) {
                Box(modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0x99E5E7EB))
                            .clickable{},
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.ShoppingCart,
                        contentDescription = "shoping",
                        tint = Color.Blue
                    )
                }
                Spacer(Modifier.width(8.dp))
                Box(modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0x99E5E7EB))
                    .clickable{},
                    contentAlignment = Alignment.Center
                )  {
                    Icon(
                        imageVector = Icons.Outlined.CardGiftcard,
                        contentDescription = "regalo",
                        tint = Color.Green
                    )
                }
            }
        }
    )
}