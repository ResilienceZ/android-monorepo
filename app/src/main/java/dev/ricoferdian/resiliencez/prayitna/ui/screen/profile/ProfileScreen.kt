package dev.ricoferdian.resiliencez.prayitna.ui.screen.profile

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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import dev.ricoferdian.resiliencez.prayitna.ui.screen.emergency_call.component.ErrorImageContent
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme
import kotlinx.coroutines.launch

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

    val coroutineScope = rememberCoroutineScope()

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
                        .clickable { },
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
                        .clickable { },
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
        sheetPeekHeight = 0.dp // Hide the sheet when it is collapsed
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