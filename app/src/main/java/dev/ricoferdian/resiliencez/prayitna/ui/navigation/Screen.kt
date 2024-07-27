package dev.ricoferdian.resiliencez.prayitna.ui.navigation

sealed class Screen(val route: String) {
    object Splash: Screen("emergency-call")
}