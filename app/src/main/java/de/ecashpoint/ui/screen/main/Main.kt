package de.ecashpoint.ui.screen.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Feed
import androidx.compose.material.icons.filled.Feed
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Feed
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import de.ecashpoint.data.models.BottomNavItems
import de.ecashpoint.ui.componets.TopBarMain
import de.ecashpoint.ui.screen.config.ConfigScreen
import de.ecashpoint.ui.screen.feed.FeedScreen
import de.ecashpoint.ui.screen.home.HomeScreen
import de.ecashpoint.ui.screen.offers.OffersScreen
import de.ecashpoint.ui.screen.profile.ProfileScreen
import kotlinx.coroutines.selects.select

@Composable
fun MainScreen(
    navController: NavController,
    onLogout : () -> Unit
){

    val navControllerInterno = rememberNavController()
    val bottomsItems = listOf(
        BottomNavItems("Start" , Icons.Default.Home  , Icons.Outlined.Home, "home" ),
        BottomNavItems("Feed" , Icons.AutoMirrored.Filled.Feed, Icons.Outlined.Feed, "feed"),
        BottomNavItems("Angebote" , Icons.Default.LocalOffer , Icons.Outlined.LocalOffer, "offert"),
        BottomNavItems("Profil" , Icons.Default.Person ,Icons.Outlined.Person, "profile"),
        BottomNavItems("Einstellungen" , Icons.Default.Settings, Icons.Outlined.Settings , "settings")
    )

    Box(modifier = Modifier.fillMaxSize().background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFF9D670),
                Color(0xFFD4EFF6),
                Color(0xFF96C97C)
                )
        )
    )){

        Scaffold(containerColor = Color.Transparent ,
            /*topBar = {
                TopBarMain()
            },*/
            bottomBar = {
                NavigationBar(
                    containerColor = Color.White,
                    modifier = Modifier.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))) {
                    bottomsItems.forEach { item ->
                        val isSelected = navController.currentDestination?.route == item.route
                        NavigationBarItem(
                            selected = isSelected,
                            onClick = {
                                navControllerInterno.navigate(item.route){
                                    popUpTo(navControllerInterno.graph.findStartDestination().id){
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = if(isSelected) item.icon else item.iconOutlend,
                                    contentDescription = item.title,
                                    modifier = Modifier.size(20.dp)
                                )
                            },
                            label = {
                                Text(item.title)
                            },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF2196F3), // Azul Material
                                selectedTextColor = Color(0xFF2196F3),
                                unselectedIconColor = Color(0xFF757575), // Gris
                                unselectedTextColor = Color(0xFF757575),
                                indicatorColor = Color.Transparent // Esto quita el fondo del item seleccionado
                            )
                        )
                    }
                }
            }, content = {innerPadding ->
                NavHost(
                    navController = navControllerInterno,
                    startDestination = "home",
                    modifier = Modifier.padding(bottom =  innerPadding.calculateBottomPadding())
                ){
                    composable("home") { HomeScreen() }
                    composable("feed") { FeedScreen() }
                    composable("offert") { OffersScreen() }
                    composable("profile") { ProfileScreen() }
                    composable("settings") { ConfigScreen() }
                }
            }
        )
    }
}