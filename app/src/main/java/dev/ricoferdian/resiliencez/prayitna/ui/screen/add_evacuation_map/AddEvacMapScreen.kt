package dev.ricoferdian.resiliencez.prayitna.ui.screen.add_evacuation_map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.ricoferdian.resiliencez.prayitna.ui.screen.add_evacuation_map.component.ItemFieldSelection
import dev.ricoferdian.resiliencez.prayitna.ui.screen.profile.component.ItemFieldProfile
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme

@Composable
fun AddEvacMapScreen(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColor.White)
    ) {
        Column {
            ItemFieldProfile(
                title = "Name",
                textPlaceHolder = "",
                textInputed = "Kantor",
                modifier = Modifier
                    .background(CustomColor.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp),
                isEditable = false
            )

            ItemFieldSelection(
                textPlaceholder = "Set Pin Location",
                inputtedText = null,
                title = "Location",
                modifier = Modifier
                    .clickable {
                        // handle nav to map select pin
                    }
            )


        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmergencyCallScreenPreview() {
    PrayitnaTheme {
        AddEvacMapScreen()
    }
}