package de.ecashpoint.ui.navigation

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import de.ecashpoint.ui.screen.login.LoginScreen
import de.ecashpoint.ui.screen.login.LoginViewModel
import de.ecashpoint.ui.screen.main.MainScreen
import de.ecashpoint.ui.screen.splash.SplashScreen
import kotlin.math.log

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object Main : Screen("main")
    object Slah : Screen("slash")
}

@Composable
fun NavGraph(navController: NavHostController
) {

    val loginViewModel : LoginViewModel = viewModel()
    NavHost(
        navController = navController,
        startDestination = Screen.Slah.route
    ) {
        composable(
            Screen.Slah.route,
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = 800,
                        easing = FastOutLinearInEasing
                    )
                )
            },
            popEnterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 800
                    )
                )
            }
        ) {
            SplashScreen(navController = navController , loginViewModel = loginViewModel)
        }

        composable(
            Screen.Login.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 1500,
                        delayMillis = 600, // Pequeño delay para que se note más
                        easing = LinearOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = 800
                    )
                )
            }
        ) {
            LoginScreen(
                navController = navController,
                loginViewModel = loginViewModel
            )
        }

        composable(
            Screen.Main.route,
            enterTransition = {
                fadeIn(
                    animationSpec = tween(
                        durationMillis = 1500,
                        delayMillis = 600, // Pequeño delay para que se note más
                        easing = LinearOutSlowInEasing
                    )
                )
            },
            exitTransition = {
                fadeOut(
                    animationSpec = tween(
                        durationMillis = 800
                    )
                )
            }
        ) {
            MainScreen(
                navController = navController,
                onLogout = {
                    loginViewModel.logout()
                    navController.navigate(Screen.Login.route){
                        popUpTo(Screen.Main.route) {inclusive = true}
                    }
                }
            )
        }
    }
}