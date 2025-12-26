package com.app.planter.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "plants")
data class PlantEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    @SerializedName("latinName")
    val latinName: String,
    val light: String,
    val water: String,
    @SerializedName("CareLevel")
    val careLevel: String,
    val imageUrl: String,
    val description: String
)