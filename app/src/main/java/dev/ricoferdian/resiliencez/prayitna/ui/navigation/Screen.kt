package dev.ricoferdian.resiliencez.prayitna.ui.navigation

sealed class Screen(val route: String) {
    object EmergencyCall: Screen("emergency-call")

    object Profile: Screen("profile")

    object LocationSelection: Screen("location-selection")

    object EvacMapList: Screen("evac-map-list")

    object AddEvacMap: Screen("add-evac-map")
}