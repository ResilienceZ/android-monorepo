package dev.ricoferdian.resiliencez.prayitna.ui.screen.emergency_call.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.ricoferdian.resiliencez.prayitna.R
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme

@Composable
fun ErrorImageContent(
    modifier: Modifier = Modifier,
    width: Dp = 130.dp,
    height: Dp = 130.dp
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        Image(
            painter = painterResource(R.drawable.ic_image_failed_load_24),
            contentDescription = null,
            colorFilter = ColorFilter.tint(CustomColor.CadetGray),
            modifier = Modifier
                .height(height)
                .width(width)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorImageContentPreview() {
    PrayitnaTheme {
        ErrorImageContent()
    }
}