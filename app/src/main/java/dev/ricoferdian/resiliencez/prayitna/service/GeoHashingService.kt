package dev.ricoferdian.resiliencez.prayitna.service

interface GeoHashingService {
    fun encode(latitude: Double, longitude: Double, precision: Int = 12): String
}