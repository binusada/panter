package com.app.planter.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.planter.data.local.dao.PlantDao
import com.app.planter.data.local.entity.PlantEntity

@Database(entities = [PlantEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun plantDao(): PlantDao
}