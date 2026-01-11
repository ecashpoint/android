package de.ecashpoint.ui.screen.ecommerce

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import de.ecashpoint.data.models.response.Ecommerce
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import de.ecashpoint.data.models.response.TypeImagen
import de.ecashpoint.R
import de.ecashpoint.data.models.response.Categorie
import java.nio.file.WatchEvent


@Composable
fun CardEcommerce(ecommerce: Ecommerce) {
    var thumbnail =
        ecommerce.assets.find { it.status == "ACTIVE" && it.type == TypeImagen.THUMBNAIL.toString() }
    var name = if(ecommerce.ecommerceName.length > 11){  "${ecommerce.ecommerceName.uppercase().take(11)}.."}else{ecommerce.ecommerceName.uppercase()}
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.FavoriteBorder,
                tint = Color.Black,
                contentDescription = "Favorite"
            )
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Black,
                fontWeight = FontWeight.Bold
            )
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            elevation = CardDefaults.elevatedCardElevation(4.dp)
        ) {


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {


                AsyncImage(
                    model = thumbnail?.secureUrl ?: R.drawable.placeholder,
                    contentDescription = "Banner",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .aspectRatio(2f / 3f)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop,

                    )
                /*Text(
                        text = ecommerce.ecommerceName,
                        color = Color.LightGray,
                        style = MaterialTheme.typography.labelSmall,

                    )*/
                /*Row(modifier = Modifier.fillMaxWidth()) {
                        for (category in ecommerce.categories) {
                            CardCategory(category)
                        }
                    }*/
                /* Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Icon(
                                imageVector = Icons.Default.LocationOn,
                                contentDescription = "Ubicación",
                                tint = Purple40
                            )
                            Text(
                                text = ecommerce.ecommerceAddress.uppercase(),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.LightGray
                            )
                        }
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {Box(modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Purple40)
                            .clickable{},
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.RemoveRedEye,
                                contentDescription = "vermas",
                                tint = Color.White
                            )
                        }
                        }
                    }*/
            }

        }

        Row(modifier = Modifier.fillMaxWidth().padding(start = 16.dp)) {
            Icon(imageVector = Icons.Default.LocationOn , modifier = Modifier.size(15.dp) , contentDescription = "location" , tint = Color.Gray)
            Text(ecommerce.ecommerceAddress.uppercase() , style = MaterialTheme.typography.bodySmall , color = Color.Gray)
        }
    }
}

@Composable
fun CardCategory(category: Categorie) {
    Card(
        modifier = Modifier
            .wrapContentWidth()
            .padding(4.dp), colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF1A6A6),
            contentColor = Color(0xFFF44336)
        )
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Text(text = category.category, style = MaterialTheme.typography.labelSmall)
        }
    }
}