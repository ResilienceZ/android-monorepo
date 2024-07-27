package dev.ricoferdian.resiliencez.prayitna.ui.screen.location_selection

import android.preference.PreferenceManager
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun LocationSelectScreen(
    modifier: Modifier = Modifier,
    viewModel: LocationSelectViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var address by remember { mutableStateOf("") }
//    val nominatimService = remember { NominatimService.create() }
    var mapView by remember { mutableStateOf<MapView?>(null) }
    var mapMarker by remember { mutableStateOf<Marker?>(null) }

    val loadingValue = viewModel.loadingState.collectAsState()
    val reverseAddress by viewModel.addressState.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = {
                Configuration.getInstance().load(context, PreferenceManager.getDefaultSharedPreferences(context))
                MapView(context).apply {
                    setMultiTouchControls(true)
                    controller.setZoom(15.0)
                    controller.setCenter(GeoPoint(-6.200000, 106.816666)) // Center on Jakarta

                    mapView = this

                    setOnTouchListener { _, event ->
                        if (event.action == android.view.MotionEvent.ACTION_UP) {
                            val geoPoint = projection.fromPixels(event.x.toInt(), event.y.toInt()) as GeoPoint
                            mapMarker?.let { overlays.remove(it) }
                            val marker = Marker(this).apply {
                                position = geoPoint
                                setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                            }
                            overlays.add(marker)
                            mapMarker = marker
                                viewModel.getEvacuationMapList(geoPoint.latitude, geoPoint.longitude)

                        }
                        false
                    }
                }
            },
            modifier = Modifier.weight(1f)
        )

        Text(
            text = reverseAddress?.display_name ?: "Got Null",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LocationSelectScreenPreview() {
    PrayitnaTheme {
        LocationSelectScreen()
    }
}