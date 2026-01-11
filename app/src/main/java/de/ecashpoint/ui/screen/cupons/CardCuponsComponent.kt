package de.ecashpoint.ui.screen.cupons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun CardCuponsComponent(){

    Column(modifier = Modifier.fillMaxWidth().padding(16.dp) , verticalArrangement = Arrangement.Center) {
        Text(text = "Mitmachend und Gewinnen" , style = MaterialTheme.typography.titleMedium, color = Color.Black)

        Card(modifier = Modifier
            .fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFDD7D5D5)
            ),
            elevation = CardDefaults.elevatedCardElevation(16.dp)) {
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = {} , colors = IconButtonDefaults.iconButtonColors(
                        containerColor = Color.Gray
                    )) {
                        Icon(imageVector = Icons.Outlined.Lock , contentDescription = "Bloqueado" , tint = Color.White)

                    }

                    Column {
                        Text(text = "Kausen Si ein und verdien..." , style = MaterialTheme.typography.titleLarge, color = Color.Black , fontWeight = FontWeight.Bold)
                        Text(text = "SchieBen Sie einen Kauf ab,um zuspiele" , style = MaterialTheme.typography.bodySmall , color = Color.LightGray)
                    }
                }
                Spacer(Modifier.height(150.dp))
            }
        }
    }

}