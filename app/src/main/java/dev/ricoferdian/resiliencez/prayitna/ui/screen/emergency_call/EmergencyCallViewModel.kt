package dev.ricoferdian.resiliencez.prayitna.ui.screen.emergency_call

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ricoferdian.resiliencez.prayitna.data.MainRepository
import dev.ricoferdian.resiliencez.prayitna.data.model.EmergencyItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EmergencyCallViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _emergencyCallItemsState = MutableStateFlow<List<EmergencyItemModel>>(emptyList())
    val emergencyCallItemsState: StateFlow<List<EmergencyItemModel>> = _emergencyCallItemsState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    init {
        getEmergencyCallList()
    }

    fun getEmergencyCallList() {
        _loadingState.value = true
        try {
            viewModelScope.launch {
                mainRepository.getEmergencyList()
                    .catch {
                        // handle error
                    }
                    .collect { emergencyList ->
                        _emergencyCallItemsState.value = emergencyList
                    }
            }
        } catch (e: Exception) {
            // handle error get emergency list
            _loadingState.value = false
        }
    }
}