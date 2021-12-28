package com.tnk.project_ions.db

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [Meal::class],
    version = 4
)

abstract class MealsDatabase : RoomDatabase() {

    abstract fun mealsDao(): MealsDao

    private class ProjectsDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        var runOnce: Boolean = false

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    //db.
                    //populateDatabase(database.projectDao())
                    Log.v("MealDatabase", "Populating Meals Db")
                    for (things in ENUM_DAYS.values()) {
                        for (otherThings in ENUM_TIMES.values()) {
                            val newMeal = Meal(
                                0,
                                ENUM_MEAL_TYPES.DEFAULT.toString(),
                                "Default Meal",
                                ENUM_MEAL_STATUS.OPEN.toString(),
                                "",
                                "",
                                "",
                                "",
                                things.toString(),
                                otherThings.toString()
                            )
                            database.mealsDao().insertMeal(newMeal)
                            Log.v("MealDatabase", "Day " + things.ordinal +
                            " Meal " + otherThings.ordinal)
                        }
                    }
                }
            }
        }
    }

    companion object {
        //Singleton prevents multiple instances of the database opening at the same time
        @Volatile
        private var INSTANCE: MealsDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): MealsDatabase {
            // if the INSTANCE is not null, then return it
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealsDatabase::class.java,
                    "meals_database"
                )
                    .fallbackToDestructiveMigration()
                    // TODO Make the Query calls Async
                    .allowMainThreadQueries()
                    .addCallback(ProjectsDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                //return instance
                instance
            }
        }
    }
}

