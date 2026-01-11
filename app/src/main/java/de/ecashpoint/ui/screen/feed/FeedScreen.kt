package de.ecashpoint.ui.screen.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import de.ecashpoint.data.models.response.Ecommerce
import de.ecashpoint.data.models.response.UiStateEcommerce
import de.ecashpoint.ui.componets.TopBarFeed
import de.ecashpoint.ui.screen.ecommerce.EcommerceViewModel

@Composable
fun FeedScreen(
    ecommerceViewModel: EcommerceViewModel = viewModel()
){
    val uiStateCommerce by ecommerceViewModel.uiStateEcommerce.collectAsState()

    LaunchedEffect(Unit) {
        ecommerceViewModel.getAll()
    }

    Box(modifier = Modifier.fillMaxSize().background(Color(0xFF141C2F))){
        TopBarFeed()

                LazyRow(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp),) {
                    when(val state = uiStateCommerce){
                        is UiStateEcommerce.Loading->{

                        }
                        is UiStateEcommerce.Empty -> {

                        }
                        is UiStateEcommerce.Error -> {

                        }
                        is UiStateEcommerce.Success->{
                            items(state.response.ecommerces) { ecommerce ->
                                EcommerceStoryItems(ecommerce = ecommerce)

                            }
                        }
                    }
                }

    }
}

@Composable
fun EcommerceStoryItems(ecommerce : Ecommerce){

    Column(horizontalAlignment = Alignment.CenterHorizontally ,
        modifier = Modifier.width(80.dp).clickable(onClick = {})) {

        Box(modifier = Modifier.size(72.dp)){

            Box(modifier = Modifier.fillMaxSize().border( width = 3.dp,
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF833AB4),
                        Color(0xFFFD1D1D),
                        Color(0xFFFCAF45)
                    )
                ),
                shape = CircleShape
            ))

            AsyncImage(
                model = ecommerce.assets[0].secureUrl ?: "https://via.placeholder.com/150",
                contentDescription = "comercion con feedd",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = ecommerce.ecommerceName,
                fontSize = 12.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}