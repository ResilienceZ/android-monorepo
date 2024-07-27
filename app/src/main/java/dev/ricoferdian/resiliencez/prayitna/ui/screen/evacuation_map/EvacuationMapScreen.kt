package dev.ricoferdian.resiliencez.prayitna.ui.screen.evacuation_map

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.ricoferdian.resiliencez.prayitna.ui.screen.evacuation_map.component.ItemMapEvacuation
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme

@Composable
fun EvacuationMapScreen(
    modifier: Modifier = Modifier,
    viewModel: EvacuationMapViewModel = hiltViewModel()
){
    val loadingValue = viewModel.loadingState.collectAsState()
    val evacMapList by viewModel.evacMapItemsState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(evacMapList, key = { it.id }) { evacMapItem ->
                ItemMapEvacuation(
                    namePlace = evacMapItem.name,
                    imagePlaceUrl = evacMapItem.imageUrl,
                    modifier = Modifier
                        .clickable {
                            //handle on map click
                        }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmergencyCallScreenPreview() {
    PrayitnaTheme {
        EvacuationMapScreen()
    }
}