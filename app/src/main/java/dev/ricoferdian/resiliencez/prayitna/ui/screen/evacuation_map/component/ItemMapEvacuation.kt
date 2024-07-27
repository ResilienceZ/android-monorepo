package dev.ricoferdian.resiliencez.prayitna.ui.screen.evacuation_map.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import dev.ricoferdian.resiliencez.prayitna.R
import dev.ricoferdian.resiliencez.prayitna.ui.screen.emergency_call.component.ErrorImageContent
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme


@Composable
fun ItemMapEvacuation(
    namePlace: String,
    imagePlaceUrl: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(
                CustomColor.White,
                shape = RoundedCornerShape(8.dp)
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SubcomposeAsyncImage(
            model = imagePlaceUrl,
            contentDescription = null,
            loading = { },
            error = {
                ErrorImageContent()
            },
            modifier = Modifier
                .width(70.dp)
                .fillMaxHeight(),
            contentScale = ContentScale.Crop,
        )

        Column {
            Text(
                text = namePlace,
                color = CustomColor.Black,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Image(
            painter = painterResource(id = R.drawable.ic_chevron_right_24),
            contentDescription = null,
            colorFilter = ColorFilter.tint(CustomColor.Black),
            modifier = Modifier
                .padding(end = 16.dp)
                .height(30.dp)
                .width(30.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun EmergencyCallScreenPreview() {
    PrayitnaTheme {
        ItemMapEvacuation(
            namePlace = "Kantor",
            imagePlaceUrl = "",
        )
    }
}