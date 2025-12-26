package com.app.planter.data.repository

import android.util.Log
import com.app.planter.data.local.dao.PlantDao
import com.app.planter.data.remote.JsonDataSource
import com.app.planter.ui.models.Plant
import com.app.planter.ui.models.PlantStatus
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlantRepository @Inject constructor(
    private val plantDao: PlantDao,
    private val jsonDataSource: JsonDataSource,
    private val ioDispatcher: CoroutineDispatcher
) {

    val allPlants = plantDao.getAllPlants().map {
        it.map { plantEntity ->
            Plant(
                id = plantEntity.id,
                name = plantEntity.name,
                species = plantEntity.latinName,
                moisture = 50, // Bogus data for now
                light = plantEntity.light,
                careLevel = plantEntity.careLevel,
                imageUrl = plantEntity.imageUrl,
                description = plantEntity.description,
                status = PlantStatus.Healthy // Bogus data for now
            )
        }
    }

    suspend fun getPlantById(plantId: Int): Plant? {
        return allPlants.map { plants -> plants.find { it.id == plantId } }.first()
    }

    suspend fun refreshPlantsIfNeeded(forceRefresh: Boolean = false) = withContext(ioDispatcher) {
        Log.d("--", "---PlantRepository:forcedRefresh---$forceRefresh")

        if(forceRefresh || plantDao.getPlantCount() == 0) {
            try {
                val plants = jsonDataSource.getIndoorPlantsUK() // Simulate remote
                Log.d("--", "---PlantRepository:json-file---$plants")

                plantDao.insertAll(plants)
                Log.d("--", "---PlantRepository:inserted---")

            } catch (e: Exception) {
                Log.e("--", "---PlantRepository:Exception --- ")
                e.printStackTrace()

            }
        }
    }
}