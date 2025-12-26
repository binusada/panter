package com.app.planter

import android.app.Application
import com.app.planter.domain.usecase.GetPlantsUseCase
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class PlanterApp : Application() {
    @Inject
    lateinit var getPlantsUseCase: GetPlantsUseCase

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.IO).launch {
            getPlantsUseCase.refreshIfNeeded(true)
        }
    }
}