package de.ecashpoint.ui.screen.splash
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import kotlinx.coroutines.delay
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Popup
import androidx.media3.ui.PlayerView
import de.ecashpoint.R
import de.ecashpoint.ui.navigation.Screen
import de.ecashpoint.ui.screen.login.LoginViewModel
import kotlin.math.log

@Composable
fun SplashScreen(
    navController: NavController,
    loginViewModel: LoginViewModel
) {
    val authUiState = loginViewModel.authUiState
    val context = LocalContext.current
    var state by remember { mutableStateOf(UiState.Loading) }
    var shouldNavigate by remember { mutableStateOf(false) }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val videoUri = "android.resource://${context.packageName}/${R.raw.into}"
            val mediaItem = MediaItem.fromUri(videoUri)
            setMediaItem(mediaItem)
            prepare()
        }
    }

    // Validación de autenticación
    LaunchedEffect(authUiState.isLoading) {
        if (!authUiState.isLoading) {
            if (authUiState.isLoggedIn) {
                // Si ya está logueado, saltar directo al Main
                delay(500) // Pequeño delay para transición suave
                navController.navigate(Screen.Main.route) {
                    popUpTo(Screen.Slah.route) { inclusive = true }
                }
                shouldNavigate = true
            } else {
                // Si NO está logueado, mostrar el splash completo
                delay(2000) // Loading inicial
                state = UiState.Loaded
                exoPlayer.playWhenReady = true
            }
        }
    }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(playbackState: Int) {
                if (playbackState == Player.STATE_ENDED && !shouldNavigate) {
                    state = UiState.Error // Pantalla blanca final
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    // Navegar al Login después de la pantalla blanca
    LaunchedEffect(state) {
        if (state == UiState.Error && !shouldNavigate) {
            delay(1000) // Pantalla blanca por 1 segundo
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Slah.route) { inclusive = true }
            }
        }
    }

    // Solo mostrar animación si no está navegando directamente
    if (!shouldNavigate) {
        AnimatedContent(
            targetState = state,
            transitionSpec = {
                fadeIn(
                    animationSpec = tween(1000)
                ) togetherWith fadeOut(
                    animationSpec = tween(1000)
                )
            },
            label = "Splash Animation"
        ) { targetState ->
            when (targetState) {
                UiState.Loading -> {
                    LoadingScreen()
                }
                UiState.Loaded -> {
                    VideoScreen(exoPlayer)
                }
                UiState.Error -> {
                    BlankScreen()
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = Color(0xFF6200EE),
            strokeWidth = 4.dp
        )
    }
}

@Composable
fun VideoScreen(exoPlayer: ExoPlayer) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F8F8))
    ) {
        AndroidView(
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    useController = false
                    setBackgroundColor(android.graphics.Color.parseColor("#F8F8F8"))
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
fun BlankScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFF9D670),
                        Color(0xFFD4EFF6),
                        Color(0xFF96C97C),
                    )
                )
            )
    )
}

enum class UiState {
    Loading,
    Loaded,
    Error
}