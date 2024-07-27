package dev.ricoferdian.resiliencez.prayitna.data.model

data class EvacMapItemModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val address: String,
    val latitude: Double,
    val longitude: Double
)