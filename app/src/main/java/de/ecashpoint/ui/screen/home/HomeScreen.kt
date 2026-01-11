package de.ecashpoint.ui.screen.home
 
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.ecashpoint.ui.screen.category.CategoryScreen
import de.ecashpoint.ui.screen.cupons.CardCuponsComponent
import de.ecashpoint.ui.screen.ecommerce.EcommerceViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import de.ecashpoint.data.models.response.UiStateEcommerce
import de.ecashpoint.ui.componets.TopBarMain
import de.ecashpoint.ui.screen.ecommerce.CardEcommerce

@Composable
fun HomeScreen(
    ecommerceViewModel: EcommerceViewModel = viewModel()
) {
    val uiStateCommerce by ecommerceViewModel.uiStateEcommerce.collectAsState()

    LaunchedEffect(Unit) {
        ecommerceViewModel.getAll()
    }
    Column(modifier = Modifier.fillMaxSize()) {
        TopBarMain()
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 8.dp
        ) {


            item(span = StaggeredGridItemSpan.FullLine) {
                CardCuponsComponent()
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                CategoryScreen()
            }

            item(span = StaggeredGridItemSpan.FullLine) {
                Spacer(Modifier.height(16.dp))
            }

            when (uiStateCommerce) {
                is UiStateEcommerce.Loading -> {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Box(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is UiStateEcommerce.Empty -> {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text("No hay comercios disponibles")
                    }
                }

                is UiStateEcommerce.Error -> {
                    item(span = StaggeredGridItemSpan.FullLine) {
                        Text("Error al cargar comercios")
                    }
                }

                is UiStateEcommerce.Success -> {
                    // No hacer nada aquí
                }
            }

            // Items del grid (ocupan 1 columna cada uno)
            if (uiStateCommerce is UiStateEcommerce.Success) {
                val ecommerces = (uiStateCommerce as UiStateEcommerce.Success).response.ecommerces
                items(
                    count = ecommerces.size,
                    key = { index -> ecommerces[index].id }
                ) { index ->
                    CardEcommerce(ecommerce = ecommerces[index])
                }
            }
        }
    }
}

/*fun HomeScreen(){
    val verticalScroll = rememberScrollState()

    Box(modifier = Modifier.background(Color.Transparent).fillMaxSize().verticalScroll(rememberScrollState())) {
        Column(modifier = Modifier.fillMaxSize()) {
            CardCuponsComponent()
            CategoryScreen()
            Spacer(Modifier.height(16.dp))
            ListEcommerce()
        }
    }
}*/