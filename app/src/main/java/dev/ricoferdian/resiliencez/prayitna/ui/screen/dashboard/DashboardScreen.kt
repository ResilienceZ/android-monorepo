package dev.ricoferdian.resiliencez.prayitna.ui.screen.dashboard

import android.preference.PreferenceManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var mapView by remember { mutableStateOf<MapView?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(CustomColor.White),
        contentAlignment = Alignment.BottomCenter
    ) {
        AndroidView(
            factory = {
                Configuration.getInstance()
                    .load(context, PreferenceManager.getDefaultSharedPreferences(context))
                MapView(context).apply {
                    setMultiTouchControls(true)
                    controller.setZoom(15.0)
                    controller.setCenter(GeoPoint(-6.200000, 106.816666)) // Center on Jakarta
                    mapView = this
                }
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}