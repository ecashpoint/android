package de.ecashpoint.ui.componets

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CardGiftcard
import androidx.compose.material.icons.outlined.NotificationAdd
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarFeed(){
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
        title = {
            Column(verticalArrangement = Arrangement.Center , horizontalAlignment = Alignment.Start) {
                Text(
                    text = "Entdecken",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(text = "Die besten Angebote für Sie", style = MaterialTheme.typography.bodySmall, color = Color.White)
            }
        },
        actions = {
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Absolute.Right) {
                Box(modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1F2844))
                    .clickable{},
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "search",
                        tint = Color.White
                    )
                }
                Spacer(Modifier.width(8.dp))
                Box(modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF1F2844))
                    .clickable{},
                    contentAlignment = Alignment.Center
                )  {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "notifications",
                        tint = Color.White
                    )
                }
            }
        }
    )
}