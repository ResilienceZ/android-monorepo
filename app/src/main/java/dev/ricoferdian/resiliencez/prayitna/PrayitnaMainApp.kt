package dev.ricoferdian.resiliencez.prayitna

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import me.pushy.sdk.Pushy

@HiltAndroidApp
class PrayitnaMainApp: Application() {
    override fun onCreate() {
        super.onCreate()

        Pushy.listen(this)
    }
}