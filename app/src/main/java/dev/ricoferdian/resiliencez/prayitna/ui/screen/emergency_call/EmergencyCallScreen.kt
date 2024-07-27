package dev.ricoferdian.resiliencez.prayitna.ui.screen.emergency_call

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import dev.ricoferdian.resiliencez.prayitna.R
import dev.ricoferdian.resiliencez.prayitna.ui.screen.emergency_call.component.ItemEmergencyCall
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme

@Composable
fun EmergencyCallScreen(
    viewModel: EmergencyCallViewModel = hiltViewModel()
) {

    val loadingValue = viewModel.loadingState.collectAsState()
    val emergencyList by viewModel.emergencyCallItemsState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColor.White)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            items(emergencyList, key = { it.id }) { emergencyItem ->
                ItemEmergencyCall(
                    namePlace = emergencyItem.name,
                    imagePlaceUrl = emergencyItem.imageUrl,
                    phoneNumberPlace = emergencyItem.phoneNumber,
                    onCallClicked = {
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmergencyCallScreenPreview() {
    PrayitnaTheme {
        EmergencyCallScreen()
    }
}