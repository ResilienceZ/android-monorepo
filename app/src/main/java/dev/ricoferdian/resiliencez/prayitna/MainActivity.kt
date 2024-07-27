package dev.ricoferdian.resiliencez.prayitna

import android.app.Activity
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import dev.ricoferdian.resiliencez.prayitna.ui.screen.add_evacuation_map.AddEvacMapScreen
import dev.ricoferdian.resiliencez.prayitna.ui.screen.evacuation_map.EvacuationMapScreen
import dev.ricoferdian.resiliencez.prayitna.ui.screen.profile.ProfileScreen
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
//            Pushy.subscribe("jakarta", applicationContext)
//            Pushy.listen(applicationContext)
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrayitnaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )

                    AddEvacMapScreen()
                }
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
        }
        else {
            // Registration success, result is device token
            message = "Pushy device token: " + result.toString() + "\n\n(copy from logcat)"
        }

        // Display dialog
        android.app.AlertDialog.Builder(activity)
            .setTitle("Pushy")
            .setMessage(message)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }
}

class PushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // Attempt to extract the "title" property from the data payload, or fallback to app shortcut label
        val notificationTitle = if (intent.getStringExtra("title") != null) intent.getStringExtra("title") else context.packageManager.getApplicationLabel(context.applicationInfo).toString()

        // Attempt to extract the "message" property from the data payload: {"message":"Hello World!"}
        var notificationText = if (intent.getStringExtra("message") != null ) intent.getStringExtra("message") else "Test notification"

        // Prepare a notification with vibration, sound and lights
        val builder = NotificationCompat.Builder(context)
            .setAutoCancel(true)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setLights(Color.RED, 1000, 1000)
            .setVibrate(longArrayOf(0, 400, 250, 400))
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setContentIntent(PendingIntent.getActivity(context, 0, Intent(context, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE))

        // Automatically configure a Notification Channel for devices running Android O+
        Pushy.setNotificationChannel(builder, context)

        // Get an instance of the NotificationManager service
        val notificationManager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // Build the notification and display it
        //
        // Use a random notification ID so multiple
        // notifications don't overwrite each other
        notificationManager.notify((Math.random() * 100000).toInt(), builder.build())
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PrayitnaTheme {
        Greeting("Android")
    }
}