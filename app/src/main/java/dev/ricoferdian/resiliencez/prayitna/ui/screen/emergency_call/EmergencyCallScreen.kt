package dev.ricoferdian.resiliencez.prayitna.ui.screen.emergency_call

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import dev.ricoferdian.resiliencez.prayitna.ui.screen.emergency_call.component.ItemEmergencyCall
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme
import dev.ricoferdian.resiliencez.prayitna.ui.utils.showToast
import kotlinx.coroutines.launch

@Composable
fun EmergencyCallScreen(
    viewModel: EmergencyCallViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    val loadingValue = viewModel.loadingState.collectAsState()
    val emergencyList by viewModel.emergencyCallItemsState.collectAsState()
    val selectedPhoneNumber: MutableState<String?> = remember { mutableStateOf(null) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            makePhoneCall(context, selectedPhoneNumber.value ?: "")
        } else {
            showToast(context, "Permission Denied, please allow phone call permission")
        }
    }

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
                        selectedPhoneNumber.value = emergencyItem.phoneNumber
                        coroutineScope.launch {
                            when {
                                ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CALL_PHONE
                                ) == PackageManager.PERMISSION_GRANTED -> {
                                    makePhoneCall(context, emergencyItem.phoneNumber)
                                }

                                else -> {
                                    requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
                                }
                            }
                        }
                    },
                )
            }
        }
    }
}

private fun makePhoneCall(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_CALL).apply {
        data = Uri.parse("tel:$phoneNumber")
    }
    context.startActivity(intent)
}

@Preview(showBackground = true)
@Composable
fun EmergencyCallScreenPreview() {
    PrayitnaTheme {
        EmergencyCallScreen()
    }
}