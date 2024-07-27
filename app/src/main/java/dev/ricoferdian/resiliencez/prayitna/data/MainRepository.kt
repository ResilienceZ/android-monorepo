package dev.ricoferdian.resiliencez.prayitna.data

import dev.ricoferdian.resiliencez.prayitna.data.model.EmergencyItemModel
import dev.ricoferdian.resiliencez.prayitna.data.model.EvacMapItemModel
import dev.ricoferdian.resiliencez.prayitna.data.remote.OsmApiService
import dev.ricoferdian.resiliencez.prayitna.data.remote.response.AddressResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MainRepository @Inject constructor(private val osmApiService: OsmApiService) {

    suspend fun getReverseAddress(lat: Double, lon: Double, format: String): Flow<AddressResponse?> {
        return flowOf(osmApiService.getAddress(
            format = format,
            lat = lat,
            lon = lon
        ))
    }

    suspend fun getEmergencyList(): Flow<List<EmergencyItemModel>> {
        val items = ArrayList<EmergencyItemModel>()
        for (i in 1..10) {
            items.add(
                EmergencyItemModel(
                    id = i.toString(),
                    name = "Ambulance",
                    imageUrl = "",
                    phoneNumber = "+62 822 7788 9900"
                )
            )
        }

        return flowOf(items)
    }

    suspend fun getEvacuationMapList() : Flow<List<EvacMapItemModel>> {
        val items = ArrayList<EvacMapItemModel>()
        for (i in 1..< 10) {
            items.add(
                EvacMapItemModel(
                    id = i.toString(),
                    name = "Kantor",
                    imageUrl = "",
                    address = "xxx",
                    latitude = 0.0,
                    longitude = 0.0
                )
            )
        }

        return flowOf(items)
    }
}