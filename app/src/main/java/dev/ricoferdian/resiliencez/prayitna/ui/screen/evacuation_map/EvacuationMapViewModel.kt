package dev.ricoferdian.resiliencez.prayitna.ui.screen.evacuation_map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.ricoferdian.resiliencez.prayitna.data.MainRepository
import dev.ricoferdian.resiliencez.prayitna.data.model.EvacMapItemModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EvacuationMapViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : ViewModel() {

    private val _evacMapItemsState = MutableStateFlow<List<EvacMapItemModel>>(emptyList())
    val evacMapItemsState: StateFlow<List<EvacMapItemModel>> = _evacMapItemsState

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    init {
        getEvacuationMapList()
    }

    fun getEvacuationMapList() {
        _loadingState.value = true
        try {
            viewModelScope.launch {
                mainRepository.getEvacuationMapList()
                    .catch {
                        // handle error
                    }
                    .collect { evacMapList ->
                        _evacMapItemsState.value = evacMapList
                    }
            }
        } catch (e: Exception) {
            // handle error get emergency list
            _loadingState.value = false
        }
    }
}