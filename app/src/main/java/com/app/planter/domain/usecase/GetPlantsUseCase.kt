package com.app.planter.domain.usecase

import com.app.planter.data.repository.PlantRepository
import javax.inject.Inject

class GetPlantsUseCase @Inject constructor(
    private val repository: PlantRepository
) {
    val plantsFlow = repository.allPlants

    suspend fun refreshIfNeeded(force: Boolean = false) {
        repository.refreshPlantsIfNeeded(force)
    }
}