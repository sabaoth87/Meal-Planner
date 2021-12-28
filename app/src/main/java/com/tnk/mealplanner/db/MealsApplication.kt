package com.tnk.project_ions.db

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class MealsApplication: Application() {
    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())
    // Using my lazy sot he database and the repo are only created when they're needed
    // rather than when the application starts
    val database by lazy { MealsDatabase.getDatabase(this, applicationScope)}
    val repo by lazy { MealRepository(database.mealsDao()) }
}