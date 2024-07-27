package dev.ricoferdian.resiliencez.prayitna.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import dev.ricoferdian.resiliencez.prayitna.ui.screen.profile.component.ItemFieldProfile
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

            HorizontalDivider(
                modifier = Modifier
                    .padding(vertical = 16.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(5) {
                    ItemFieldProfile(
                        title = "Email: ",
                        textPlaceHolder = "Input your email",
                        isEditable = true,
                        onChangeText = {},
                        modifier = Modifier
                            .background(CustomColor.Gray10, shape = RoundedCornerShape(16.dp))
                            .padding(16.dp)
                    )
                }
            }


        }




    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    PrayitnaTheme {
        ProfileScreen()
    }
}