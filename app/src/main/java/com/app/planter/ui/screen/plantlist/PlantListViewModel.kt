package com.app.planter.ui.screen.plantlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import com.app.planter.domain.usecase.GetPlantsUseCase
import com.app.planter.ui.models.Plant
import com.app.planter.util.Result
import com.app.planter.util.Result.Error
import com.app.planter.util.Result.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlantListViewModel @Inject constructor(
    private val getPlantsUseCase: GetPlantsUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<Result<List<Plant>>> = MutableStateFlow(Result.Loading)
    val state: StateFlow<Result<List<Plant>>> = _state

    init {
        Log.d("--", "---VM:Init---")
        loadPlants()
    }

    private fun loadPlants() {
        viewModelScope.launch {
            getPlantsUseCase
                .plantsFlow
                .catch {
                    Log.d("--", "---VM:load plant error---")
                    _state.value = Error(message = it.message)
                }
                .collectLatest {
                    Log.d("--", "---VM:load plant success ---$it")
                    _state.value = Success(data = it)
                }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            _state.value = Result.Loading
            getPlantsUseCase.refreshIfNeeded(force = true)
        }
    }
}