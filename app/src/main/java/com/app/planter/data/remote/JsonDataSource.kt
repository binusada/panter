package com.app.planter.data.remote

import android.content.Context
import com.app.planter.data.local.entity.PlantEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class JsonDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    suspend fun getIndoorPlantsUK(): List<PlantEntity> {
        return try {
            val json = context.assets.open("supported_plant_list.json")
                .bufferedReader()
                .use { it.readText() }
            Gson().fromJson(json, object : TypeToken<List<PlantEntity>>() {}.type)
        } catch (e: Exception) {
            throw e // Simulate network error
        }
    }
}