package dev.ricoferdian.resiliencez.prayitna.ui.screen.add_evacuation_map

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import dev.ricoferdian.resiliencez.prayitna.ui.screen.add_evacuation_map.component.ItemFieldSelection
import dev.ricoferdian.resiliencez.prayitna.ui.screen.emergency_call.component.ErrorImageContent
import dev.ricoferdian.resiliencez.prayitna.ui.screen.location_selection.LocationNavModel
import dev.ricoferdian.resiliencez.prayitna.ui.screen.profile.component.ItemFieldProfile
import dev.ricoferdian.resiliencez.prayitna.ui.screen.profile.saveBitmapToFile
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme
import dev.ricoferdian.resiliencez.prayitna.ui.utils.showToast
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEvacMapScreen(
    modifier: Modifier = Modifier,
    navController: NavController?,
    onNavigateToLocationSelect: () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val location = remember { mutableStateOf<LocationNavModel?>(null) }
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
            density = LocalDensity.current
        )
    )

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
            coroutineScope.launch {
                bottomSheetState.bottomSheetState.hide()
            }
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                imageUri = saveBitmapToFile(context, it)
                coroutineScope.launch {
                    bottomSheetState.bottomSheetState.hide()
                }
            }
        }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                cameraLauncher.launch(null)
            } else {
                showToast(context, "Permission Denied")
            }
        }
    )

    LaunchedEffect(navController) {
        navController?.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("location")
            ?.observeForever { newResult ->
                val gson = Gson()
                val params = gson.fromJson(newResult, LocationNavModel::class.java)
                location.value = params
            }
    }

    BottomSheetScaffold(
        scaffoldState = bottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetDragHandle = null,
        modifier = modifier,
        sheetContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CustomColor.Gray10)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Select image from....",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = CustomColor.Black
                )

                Box(
                    modifier = Modifier
                        .background(CustomColor.DarkTangerin, shape = RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .height(40.dp)
                        .clickable {
                            launcher.launch(android.Manifest.permission.CAMERA)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Camera",
                        fontSize = 18.sp,
                        color = CustomColor.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Box(
                    modifier = Modifier
                        .background(CustomColor.DarkTangerin, shape = RoundedCornerShape(16.dp))
                        .fillMaxWidth()
                        .height(40.dp)
                        .clickable {
                            galleryLauncher.launch("image/*")
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Gallery",
                        fontSize = 18.sp,
                        color = CustomColor.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        Box(
            modifier = Modifier
                .padding(it)
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
                    isEditable = true
                )

                ItemFieldSelection(
                    textPlaceholder = "Set Pin Location",
                    inputtedText = location.value?.address,
                    title = "Location",
                    modifier = Modifier
                        .clickable(onClick = onNavigateToLocationSelect)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Evacuation Map",
                        fontSize = 16.sp,
                        color = CustomColor.Black,
                        fontWeight = FontWeight.SemiBold
                    )

                    if (imageUri == null) {
                        SubcomposeAsyncImage(
                            model = "",
                            contentDescription = null,
                            loading = { },
                            error = {
                                ErrorImageContent()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        bottomSheetState.bottomSheetState.expand()
                                    }
                                },
                            contentScale = ContentScale.Crop,
                        )
                    } else {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(imageUri)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .clickable {
                                    coroutineScope.launch {
                                        bottomSheetState.bottomSheetState.expand()
                                    }
                                },
                            contentScale = ContentScale.Crop,
                        )
                    }
                }
            }
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