package dev.ricoferdian.resiliencez.prayitna

import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.RingtoneManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.gson.Gson
import com.google.gson.JsonElement
import dagger.hilt.android.AndroidEntryPoint
import dev.ricoferdian.resiliencez.prayitna.ui.navigation.Screen
import dev.ricoferdian.resiliencez.prayitna.ui.screen.add_evacuation_map.AddEvacMapScreen
import dev.ricoferdian.resiliencez.prayitna.ui.screen.alert.AlertScreen
import dev.ricoferdian.resiliencez.prayitna.ui.screen.dashboard.DashboardScreen
import dev.ricoferdian.resiliencez.prayitna.ui.screen.emergency_call.EmergencyCallScreen
import dev.ricoferdian.resiliencez.prayitna.ui.screen.evacuation_map.EvacuationMapScreen
import dev.ricoferdian.resiliencez.prayitna.ui.screen.location_selection.LocationSelectScreen
import dev.ricoferdian.resiliencez.prayitna.ui.screen.profile.ProfileScreen
import dev.ricoferdian.resiliencez.prayitna.ui.screen.splash.SplashScreen
import dev.ricoferdian.resiliencez.prayitna.ui.theme.PrayitnaTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.pushy.sdk.Pushy

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        CoroutineScope(Dispatchers.IO).launch {
            if (!Pushy.isRegistered(this@MainActivity)) {
                RegisterForPushNotificationsAsync(this@MainActivity).execute()
            }
            Pushy.subscribe("jakarta", applicationContext)
            Pushy.listen(applicationContext)
        }

        val extraData = intent.getStringExtra("NOTIF_EXTRA")
        Log.d("LOGDEBUG", "main extra data " + extraData.toString())

        super.onCreate(savedInstanceState)
        setContent {
            PrayitnaTheme {
                RootApp(extraData = extraData)
            }
        }
    }
}

class RegisterForPushNotificationsAsync(activity: Activity) : AsyncTask<Void, Void, Any>() {
    var activity: Activity = activity

    override fun doInBackground(vararg params: Void): Any {
        try {
            // Register the device for notifications
            val deviceToken = Pushy.register(activity)

            // Registration succeeded, log token to logcat
            Log.d("Pushy", "Pushy device token: " + deviceToken)


            // Provide token to onPostExecute()
            return deviceToken
        } catch (exc: Exception) {
            // Registration failed, provide exception to onPostExecute()
            return exc
        }
    }

    override fun onPostExecute(result: Any) {
        var message: String

        // Registration failed?
        if (result is Exception) {
            // Log to console
            Log.e("Pushy", result.message.toString())

            // Display error in alert
            message = result.message.toString()
        } else {
            // Registration success, result is device token
            message = "Pushy device token: " + result.toString() + "\n\n(copy from logcat)"
        }

        // Display dialog
//        android.app.AlertDialog.Builder(activity)
//            .setTitle("Pushy")
//            .setMessage(message)
//            .setPositiveButton(android.R.string.ok, null)
//            .show()
    }
}

@Composable
fun RootApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    extraData: String?
) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val gson = Gson()
    val params = gson.fromJson(extraData, NotifData::class.java)


    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if(extraData == null) {
                Screen.Splash.route
            } else {
                Screen.Alert.createRoute(params = params)
            },
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.EmergencyCall.route) {
                EmergencyCallScreen()
            }

            composable(Screen.EvacMapList.route) {
                EvacuationMapScreen()
            }

            composable(Screen.AddEvacMap.route) {
                AddEvacMapScreen(
                    navController = navController,
                    onNavigateToLocationSelect = {
                        navController.navigate(Screen.LocationSelection.route)
                    }
                )
            }

            composable(Screen.LocationSelection.route) {
                LocationSelectScreen(
                    navController = navController
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(
                    onNavigateToEvacList = {
                        navController.navigate(Screen.EvacMapList.route)
                    }
                )
            }

            composable(
                route = Screen.Alert.route,
                arguments = listOf(navArgument("params") { type = NavType.StringType}),
                enterTransition = {
                    slideInHorizontally(
                        initialOffsetX = { it } // Slide in from the right
                    )
                },
            ) {

                AlertScreen(
                    onNavigateBack = {
                        if(navController.previousBackStackEntry == null) {
                            navController.navigate(Screen.Dashboard.route)
                        } else {
                            navController.navigateUp()
                        }
                    },
                    notifData = params

                )
            }

            composable(Screen.Dashboard.route) {
                DashboardScreen()
            }

            composable(Screen.Splash.route) {
                SplashScreen {
                    navController.navigate(Screen.Dashboard.route)
                }
            }
        }
    }
}

class PushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Attempt to extract the "title" property from the data payload, or fallback to app shortcut label
        val notificationTitle =
            if (intent.getStringExtra("title") != null) intent.getStringExtra("title") else context.packageManager.getApplicationLabel(
                context.applicationInfo
            ).toString()


        // Attempt to extract the "message" property from the data payload: {"message":"Hello World!"}
        var notificationText =
            if (intent.getStringExtra("message") != null) intent.getStringExtra("message") else "Test notification"

        var extraPayload =
            if (intent.getStringExtra("data") != null) intent.getStringExtra("data") else "Got Null"

        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("NOTIF_EXTRA", extraPayload)
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(context)
            .setAutoCancel(true)
            .setSmallIcon(R.drawable.ic_priyatna)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setLights(Color.RED, 1000, 1000)
            .setVibrate(longArrayOf(0, 400, 250, 400))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(
//                PendingIntent.getActivity(
//                    context,
//                    0,
//                    Intent(context, MainActivity::class.java),
//                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
//                )
                pendingIntent
            )

        // Automatically configure a Notification Channel for devices running Android O+
        Pushy.setNotificationChannel(builder, context)

        // Get an instance of the NotificationManager service
        val notificationManager =
            context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Build the notification and display it
        //
        // Use a random notification ID so multiple
        // notifications don't overwrite each other
        notificationManager.notify((Math.random() * 100000).toInt(), builder.build())
    }
}

fun Context.drawableToBitmap(drawableId: Int): Bitmap? {
    val drawable: Drawable? = ContextCompat.getDrawable(this, drawableId)
    if (drawable == null) {
        return null
    }
    val bitmap = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

data class NotifData (
    val description: String? = null,
    val id: JsonElement? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val scale: Double? = null,
    val severity: Long? = null,
    val timestamp: String? = null,
    val type: String? = null
)
