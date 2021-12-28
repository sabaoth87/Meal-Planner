package com.tnk.project_ions.db

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


class MealViewModel(private val mealsRepo: MealRepository) : ViewModel() {
    // Using LiveData and caching what allProjects returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only
    //   upon the UI when the data actually changes
    // - Repository is completely separated from the UI through the ViewModel
    val allMeals: LiveData<List<Meal>> = mealsRepo.allMeals.asLiveData()
    lateinit var foundMeal: Meal
    lateinit var foundMeals: Flow<List<Meal>>
    lateinit var testMeals: Flow<List<Meal>>
    lateinit var listMeals: List<Meal>
    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insert(newMeal: Meal) = viewModelScope.launch {
        mealsRepo.insert(newMeal)
    }

    fun updateMealNow(meal: Meal) = viewModelScope.launch {
        mealsRepo.updateMeal(meal)
    }

    fun findByUid(uid: Int) = viewModelScope.launch {
        foundMeal = mealsRepo.findByUid(uid)
    }

    fun returnElvStuff(one: String, two: String): List<Meal> {
        return mealsRepo.getMealsList(one, two)
    }

    fun returnListByDay(day: String): List<Meal> {
        return mealsRepo.getByDay(day)
    }

    /**
     * Ge the Meal Name of the Meal.
     *
     * @return a [Flowable] that will emit every time the user name has been updated
     */
    // for every emission of the meal, get the meal name
    fun findFlowByUid(uid: Int): Flowable<String> {
        return mealsRepo.findFlowByUID(uid)
            .map { meal -> meal.mealName}
    }

    fun testFindFlowByDayTime(day: String, time: String): Flowable<Meal> {
        return mealsRepo.testFindFlowByUID(day, time)
            .map {meal -> meal}
    }

}

class MealViewModelFactory(private val mealsRepo: MealRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MealViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MealViewModel(mealsRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}