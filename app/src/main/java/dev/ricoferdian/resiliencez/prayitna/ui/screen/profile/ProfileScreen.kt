package dev.ricoferdian.resiliencez.prayitna.ui.screen.profile

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import dev.ricoferdian.resiliencez.prayitna.ui.screen.emergency_call.component.ErrorImageContent
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme

@Composable
fun ProfileScreen(

){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            SubcomposeAsyncImage(
                model = "",
                contentDescription = null,
                loading = { },
                error = {
                    ErrorImageContent()
                },
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

            Text (
                text = "Rivaldo Fernandes",
                fontSize = 20.sp,
                color = CustomColor.Black,
                fontWeight = FontWeight.Bold
            )

            Text (
                text = "rivaldofez@gmail.com",
                fontSize = 16.sp,
                color = CustomColor.CadetGray,
                fontWeight = FontWeight.Normal
            )
        }

        HorizontalDivider()


    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    PrayitnaTheme {
        ProfileScreen()
    }
}