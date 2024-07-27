package dev.ricoferdian.resiliencez.prayitna.ui.screen.add_evacuation_map.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.ricoferdian.resiliencez.prayitna.R
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme

@Composable
fun ItemFieldSelection(
    modifier: Modifier = Modifier,
    title: String,
    inputtedText: String? = null,
    textPlaceholder: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(CustomColor.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                fontSize = 16.sp,
                color = CustomColor.Black,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = if (inputtedText.isNullOrEmpty())
                    textPlaceholder else
                    inputtedText,
                fontSize = 14.sp,
                color = if (inputtedText.isNullOrEmpty())
                    CustomColor.Black
                else
                    CustomColor.Black,
                fontWeight = FontWeight.Normal
            )


        }

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        Image(
            painter = painterResource(R.drawable.ic_chevron_right_24),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp),
            colorFilter = ColorFilter.tint(CustomColor.Black)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmergencyCallScreenPreview() {
    PrayitnaTheme {
        ItemFieldSelection(
            textPlaceholder = "Set Pin Location",
            title = "Location"
        )
    }
}