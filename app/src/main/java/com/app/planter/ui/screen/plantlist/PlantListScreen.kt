package com.app.planter.ui.screen.plantlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.planter.ui.components.PlantCard
import com.app.planter.ui.models.Plant
import com.app.planter.util.Result

@Preview
@Composable
fun PlantListScreen(
    viewModel: PlantListViewModel = hiltViewModel(),
    onPlantClick: (Plant) -> Unit = {}
) {

    val state by viewModel.state.collectAsState()

    Scaffold(
        containerColor = Color(0xFFFEFEE2), // Light yellow background
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /* TODO */ },
                containerColor = Color(0xFF558B2F), // Green FAB
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Plant")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 20.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Dr Plants",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold)
                Icon(Icons.Outlined.Notifications,
                    contentDescription = "Notifications")
            }

            when (state) {
                is Result.Loading -> {
                    CircularProgressIndicator()
                }

                is Result.Success -> {
                    val list = (state as Result.Success<List<Plant>>).data

                    LazyColumn {
                        items(list) { plant ->
                            PlantCard(
                                plant = plant,
                                onClick = { onPlantClick(plant) }
                            )
                        }
                    }
                }

                else -> {
                    Text("Error")
                }
            }
        }
    }
}