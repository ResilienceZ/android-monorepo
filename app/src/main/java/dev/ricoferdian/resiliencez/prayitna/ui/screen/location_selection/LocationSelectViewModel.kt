package dev.ricoferdian.resiliencez.prayitna.ui.screen.location_selection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ricoferdian.resiliencez.prayitna.data.MainRepository
import dev.ricoferdian.resiliencez.prayitna.data.remote.response.AddressResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationSelectViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _addressState = MutableStateFlow<AddressResponse?>(null)
    val addressState: StateFlow<AddressResponse?> = _addressState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    fun getEvacuationMapList(lat: Double, lon: Double) {
        _loadingState.value = true
        try {
            viewModelScope.launch {
                mainRepository.getReverseAddress(
                   lat = lat,
                    lon = lon,
                    format = "json"
                )
                    .catch {
                        // handle error
                    }
                    .collect { address ->
                        _addressState.value = address
                    }
            }
        } catch (e: Exception) {
            // handle error get emergency list
            _loadingState.value = false
        }
    }

}
