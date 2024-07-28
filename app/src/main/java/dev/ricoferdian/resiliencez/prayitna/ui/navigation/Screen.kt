package dev.ricoferdian.resiliencez.prayitna.ui.navigation

import android.util.Log
import com.google.gson.Gson
import dev.ricoferdian.resiliencez.prayitna.NotifData

sealed class Screen(val route: String) {
    object EmergencyCall: Screen("emergency-call")

    object Profile: Screen("profile")

    object LocationSelection: Screen("location-selection")

    object EvacMapList: Screen("evac-map-list")

    object AddEvacMap: Screen("add-evac-map")

//    object Alert: Screen("alert")

    object Dashboard: Screen("dashboard")

    object Splash: Screen("splash")

    object Alert: Screen("alert/{params}") {
        fun createRoute(params: NotifData): String {
            val gson = Gson()
            val stringParams = gson.toJson(params)

            Log.d("LOGDEBUG", "create route " + stringParams)
            return "alert/$stringParams"
        }
    }
}