package dev.ricoferdian.resiliencez.prayitna.data

import dev.ricoferdian.resiliencez.prayitna.data.model.EmergencyItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class MainRepository @Inject constructor() {
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
}