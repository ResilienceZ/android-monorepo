package dev.ricoferdian.resiliencez.prayitna.ui.screen.alert

import android.content.Context
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.ricoferdian.resiliencez.prayitna.NotifData
import dev.ricoferdian.resiliencez.prayitna.R
import dev.ricoferdian.resiliencez.prayitna.ui.theme.CustomColor
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme
import kotlinx.coroutines.launch

@Composable
fun AlertScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit,
    notifData: NotifData? = null
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    fun playAlarm() {
        var alarmUri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALL)
        if (alarmUri == null) {
            // If there is no default alarm sound, use the notification sound
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        }

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(context, alarmUri)
            isLooping = true
            prepare()
            start()
        }

        // Start continuous vibration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val pattern = longArrayOf(0, 1000, 1000) // Wait 0ms, Vibrate 1000ms, Sleep 1000ms
            vibrator.vibrate(
                VibrationEffect.createWaveform(pattern, 0) // 0 here makes the pattern repeat indefinitely
            )
        } else {
            val pattern = longArrayOf(0, 1000, 1000) // Wait 0ms, Vibrate 1000ms, Sleep 1000ms
            vibrator.vibrate(pattern, 0) // 0 here makes the pattern repeat indefinitely
        }
    }

    fun stopAlarm() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        vibrator.cancel()
    }

    LaunchedEffect(Unit) {
        playAlarm()
    }

    Box(
       modifier = Modifier
           .fillMaxSize()
           .background(CustomColor.RaddyRed)
           .pointerInput(Unit) {
               detectVerticalDragGestures { change, dragAmount ->
                   if (dragAmount < -50) {
                       coroutineScope.launch {
                            stopAlarm()
                           onNavigateBack()

                       }
                   }
               }
           },
       contentAlignment = Alignment.Center
   ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier
                    .weight(2f)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_error_24),
                contentDescription = null,
                modifier = Modifier
                    .height(150.dp)
                    .width(150.dp),
                colorFilter = ColorFilter.tint(CustomColor.White)
            )

            Text(
                text = (notifData?.type ?: "").capitalize(),
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = CustomColor.White,
                modifier = Modifier
            )

            Text(
                text = notifData?.description ?: "",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = CustomColor.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

            Image(
                painter = painterResource(id = R.drawable.ic_drop_up_24),
                contentDescription = null,
                modifier = Modifier
                    .height(40.dp)
                    .width(40.dp),
                colorFilter = ColorFilter.tint(CustomColor.White)
            )

            Text(
                text = "Swipe Up To Stop",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = CustomColor.White,
                modifier = Modifier
            )

            Spacer(
                modifier = Modifier
                    .weight(1f)
            )

        }
   }
}

@Preview(showBackground = true)
@Composable
fun AlertScreenPreview() {
    PrayitnaTheme {
        AlertScreen(
            onNavigateBack = {}
        )
    }
}