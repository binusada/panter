package com.app.planter.ui.models

import androidx.compose.ui.graphics.Color

data class Plant(
    val id: Int,
    val name: String,
    val species: String,
    val moisture: Int,
    val light: String,
    val careLevel: String,
    val imageUrl: String,
    val description: String,
    val status: PlantStatus
)

enum class PlantStatus(val label: String, val color: Color, val bgColor: Color) {
    Healthy("Healthy", Color(0xFF4CAF50), Color(0xFFE8F5E9)),
    NeedsWater("Needs Water", Color(0xFFFF7043), Color(0xFFFFF3E0)),
    LowLight("Low Light", Color(0xFFEF5350), Color(0xFFFFEBEE))
}