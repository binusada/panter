package com.app.planter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.planter.ui.screen.plantdetails.PlantDetailScreen
import com.app.planter.ui.screen.plantlist.PlantListScreen
import com.app.planter.ui.theme.PlanterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PlanterTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        PlantListScreen(onPlantClick = {
                            navController.navigate("plantDetail/${it.id}")
                        })
                    }
                    composable("plantDetail/{plantId}") { backStackEntry ->
                        val plantId = backStackEntry.arguments?.getString("plantId")?.toIntOrNull()
                        if (plantId != null) {
                            PlantDetailScreen(plantId = plantId)
                        }
                    }
                }
            }
        }
    }
}