package dev.ricoferdian.resiliencez.prayitna.ui.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.ricoferdian.resiliencez.prayitna.R
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToDashboardScreen: () -> Unit,
) {

    SplashContent()

    LaunchedEffect(Unit) {
        delay(3000L)
        onNavigateToDashboardScreen()
    }
}

@Composable
fun SplashContent(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(CustomColor.White),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.main_logo),
            contentDescription = null,
            modifier = Modifier
                .height(150.dp)
                .width(150.dp),
            contentScale = ContentScale.Crop
        )
    }
}