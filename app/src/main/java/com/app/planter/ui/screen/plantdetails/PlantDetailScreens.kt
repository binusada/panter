package com.app.planter.ui.screen.plantdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Grass
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.app.planter.ui.components.MetricBox
import com.app.planter.ui.components.RecommendationCard

@Composable
fun PlantDetailScreen(
    plantId: Int,
    viewModel: PlantDetailViewModel = hiltViewModel()
) {
    val plant by viewModel.plant.collectAsState()

    LaunchedEffect(plantId) {
        viewModel.loadPlant(plantId)
    }

    val backgroundColor = Color(0xFF1B2624) // Deep dark teal/green
    val cardColor = Color(0xFF243331)

    if (plant == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(plant!!.name, color = Color.White, style = MaterialTheme.typography.headlineSmall)
                Icon(Icons.Default.MoreVert, contentDescription = null, tint = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Large Plant Image Card
            Card(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
            ) {
                AsyncImage(
                    model = plant!!.imageUrl,
                    contentDescription = plant!!.name,
                    modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop

                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Metrics Grid (Soil, Light, Temp)
            Text("Health Metrics", color = Color.White, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                MetricBox(Modifier.weight(1f), "Soil", "${plant!!.moisture}%", "Optimal", Icons.Default.WaterDrop, cardColor)
                MetricBox(Modifier.weight(1f), "Light", plant!!.light, "Low", Icons.Default.WbSunny, cardColor)
                MetricBox(Modifier.weight(1f), "Temp", "22°C", "Perfect", Icons.Default.Thermostat, cardColor)
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Care Recommendations
            Text("Care Recommendations", color = Color.White, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            RecommendationCard(
                title = "Needs More Sunlight",
                description = "Move your plant to a brighter spot to ensure enough indirect light.",
                icon = Icons.Default.LightMode,
                iconTint = Color(0xFFFFB74D),
                containerColor = cardColor
            )

            Spacer(modifier = Modifier.height(8.dp))

            RecommendationCard(
                title = "Time to Fertilize",
                description = "It's growing season! A little plant food will encourage new growth.",
                icon = Icons.Default.Grass,
                iconTint = Color(0xFF81C784),
                containerColor = cardColor
            )
        }
    }
}