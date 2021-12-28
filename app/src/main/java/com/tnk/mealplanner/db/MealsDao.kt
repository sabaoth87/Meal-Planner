package com.tnk.project_ions.db

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface MealsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg projects: Meal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMeal(meal: Meal)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPrjLong(project: Meal): Long

    //@Insert(onConflict = OnConflictStrategy.REPLACE)
    //fun insertPrjsLong(vararg project: Project): ArrayList<Long>

    @Update
    fun updateProject(vararg notes: Meal)

    @Delete
    fun delete(vararg project: Meal)

    @Query("SELECT * FROM meals ORDER BY meal_day ASC")
    fun loadAllMeals(): Flow<List<Meal>>

    @Query("SELECT * FROM meals WHERE meal_day = :arg0")
    fun findByDay(arg0: String): List<Meal>

    @Query("SELECT * FROM meals WHERE uid = :arg0")
    fun findMealByUID(arg0: Int): Meal

    @Query("SELECT * FROM meals WHERE meal_day = :arg0 AND meal_time = :arg1")
    fun findMealByDayTime(arg0: String, arg1: String): LiveData<List<Meal>>

    @Query("SELECT * FROM meals WHERE meal_day = :arg0 AND meal_time = :arg1")
    fun listMealByDayTime(arg0: String, arg1: String): List<Meal>

    @Query("DELETE FROM meals")
    fun deleteAll(): Int

    @Query("SELECT * FROM meals WHERE uid = :arg1")
    fun findFlowByUid(arg1: Int): Flowable<Meal>

    @Query("SELECT * FROM meals WHERE meal_day = :arg0 AND meal_time = :arg1")
    fun testFindFlowByUid(arg0: String, arg1: String): Flowable<Meal>
}