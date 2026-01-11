package de.ecashpoint.ui.screen.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BorderAll
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import de.ecashpoint.data.models.response.UiStateCategory
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import de.ecashpoint.data.models.response.Categorie

@Composable
fun CategoryScreen(
    viewModel: CategoryViewModel = viewModel()
){

    val uiStateCategory by viewModel.uiStateCategory.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getCategories()
    }

    CategoryContent(uiState = uiStateCategory , onRetry = {viewModel.getCategories()})
}

@Composable
fun CategoryContent(
    uiState: UiStateCategory,
    onRetry:()-> Unit
){

    Row( modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = "Kategorien" ,
                fontWeight = FontWeight.Bold ,
                style = MaterialTheme.typography.titleMedium ,
                color = Color.Black)

            when(uiState){
                is UiStateCategory.Loading->{
                    LoadingView()
                }
                is UiStateCategory.Success ->{
                    CategoryList(categories = uiState.categorys)
                }
                is UiStateCategory.Empty ->{
                    EmptyView()
                }
                is UiStateCategory.Error ->{
                    EmptyView()
                }
            }
        }
    }

}

@Composable
fun LoadingView(){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CircularProgressIndicator()
            Text("Cargando categorías...")
        }
    }
}

@Composable
fun CategoryList(categories: List<Categorie>) {
    val category : Categorie = Categorie(id = 0 , category = "ALLE")

    if (categories.isEmpty()) {
        EmptyView()
    } else {

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    CategoryItem(category = category)
                }
                items(categories) { category ->
                    CategoryItem(category = category)
                }
            }

    }
}

@Composable
fun CategoryItem(category: Categorie) {
    val icon = when(category.category){
        "ALLE" -> Icons.Default.BorderAll
        "RESTAURANTS" -> Icons.Default.Restaurant
        "PRODUCTS" -> Icons.Default.BorderAll
        "SERVICES" -> Icons.Default.Star
        else -> Icons.Default.Category
    }
    Card (
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            contentColor = Color.White,
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = category.category,
                modifier = Modifier.size(16.dp),
                tint = Color.Blue

            )
            Text(
                text = category.category,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}

@Composable
fun EmptyView() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "No hay categorías",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "No se encontraron categorías disponibles",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ErrorView(
    error: Throwable,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.size(64.dp)
            )

            Text(
                text = "Error al cargar",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = error.message ?: "Ha ocurrido un error desconocido",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Button(
                onClick = onRetry,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Reintentar")
            }
        }
    }
}