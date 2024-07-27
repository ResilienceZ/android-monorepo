package dev.ricoferdian.resiliencez.prayitna.ui.navigation

sealed class Screen(val route: String) {
    object EmergencyCall: Screen("emergency-call")
}