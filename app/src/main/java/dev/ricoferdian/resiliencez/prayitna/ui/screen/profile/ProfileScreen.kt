package dev.ricoferdian.resiliencez.prayitna.ui.screen.profile

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import dev.ricoferdian.resiliencez.prayitna.ui.screen.emergency_call.component.ErrorImageContent
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme
import dev.ricoferdian.resiliencez.prayitna.ui.utils.showToast
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier
) {
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = SheetState(
            skipPartiallyExpanded = false,
            density = LocalDensity.current
        )
    )

    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
            Log.d("LOGDEBUG", imageUri.toString())
        }

    val cameraLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            bitmap?.let {
                imageUri = saveBitmapToFile(context, it)
            }
        }

    val coroutineScope = rememberCoroutineScope()

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
                .background(CustomColor.White)
                .fillMaxSize()
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                if (imageUri == null) {
                    SubcomposeAsyncImage(
                        model = "",
                        contentDescription = null,
                        loading = { },
                        error = {
                            ErrorImageContent()
                        },
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .clip(CircleShape)
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
                            .width(120.dp)
                            .height(120.dp)
                            .clip(CircleShape)
                            .clickable {
                                coroutineScope.launch {
                                    bottomSheetState.bottomSheetState.expand()
                                }
                            },
                        contentScale = ContentScale.Crop,
                    )
                }

                Text(
                    text = "Rivaldo Fernandes",
                    fontSize = 20.sp,
                    color = CustomColor.Black,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "rivaldofez@gmail.com",
                    fontSize = 16.sp,
                    color = CustomColor.CadetGray,
                    fontWeight = FontWeight.Normal
                )

                HorizontalDivider(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    PrayitnaTheme {
        ProfileScreen()
    }
}

fun saveBitmapToFile(context: Context, bitmap: Bitmap): Uri? {
    val filesDir = context.cacheDir
    val imageFile = File(filesDir, "captured_image_${System.currentTimeMillis()}.jpg")

    var fos: FileOutputStream? = null
    try {
        fos = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.flush()
    } catch (e: IOException) {
        e.printStackTrace()
        return null
    } finally {
        fos?.close()
    }

    return Uri.fromFile(imageFile)
}