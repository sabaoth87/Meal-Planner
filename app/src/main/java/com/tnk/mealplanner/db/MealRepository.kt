package com.tnk.project_ions.db

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow
import io.reactivex.Completable

import io.reactivex.Flowable

class MealRepository(private val projectDao: MealsDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer with the data has changed
    val allMeals: Flow<List<Meal>> = projectDao.loadAllMeals()
    //val dayMeals: Flow<List>
    // By default Room runs suspend queries off the main thread, therefore, we don't need
    // to implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(newMeal: Meal) {
        projectDao.insertMeal(newMeal)
    }

    fun findByUid(uid: Int): Meal {
        return projectDao.findMealByUID(uid)
    }

    fun updateMeal(meal: Meal) {
        projectDao.updateProject(meal)
    }

    fun getMealTest(day:String, time:String) =
        projectDao.findMealByDayTime(day, time)

    fun getMealsList(day: String, time:String) =
        projectDao.listMealByDayTime(day, time)

    fun getByDay(day: String) =
        projectDao.findByDay(day)

    fun findFlowByUID(uid: Int): Flowable<Meal> {
        return projectDao.findFlowByUid(uid)
    }

    fun testFindFlowByUID(day: String, time:String): Flowable<Meal> {
        return projectDao.testFindFlowByUid(day, time)
    }
}