package dev.ricoferdian.resiliencez.prayitna.ui.screen.add_evacuation_map

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import dev.ricoferdian.resiliencez.prayitna.ui.screen.add_evacuation_map.component.ItemFieldSelection
import dev.ricoferdian.resiliencez.prayitna.ui.screen.location_selection.LocationNavModel
import dev.ricoferdian.resiliencez.prayitna.ui.screen.profile.component.ItemFieldProfile
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme

@Composable
fun AddEvacMapScreen(
    modifier: Modifier = Modifier,
    navController: NavController?,
    onNavigateToLocationSelect: () -> Unit
) {

    val location = remember { mutableStateOf<LocationNavModel?>(null) }

    LaunchedEffect(navController) {
        navController?.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("location")
            ?.observeForever { newResult ->
                val gson = Gson()
                val params = gson.fromJson(newResult, LocationNavModel::class.java)
                location.value = params
            }
    }

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
                inputtedText = location.value?.address,
                title = "Location",
                modifier = Modifier
                    .clickable(onClick = onNavigateToLocationSelect)
            )


        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmergencyCallScreenPreview() {
    PrayitnaTheme {
        AddEvacMapScreen(
            onNavigateToLocationSelect = {},
            navController = null
        )
    }
}