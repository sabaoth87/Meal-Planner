package com.tnk.project_ions.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.reflect.KClass

@Entity(tableName = "meals")
data class Meal(
    @PrimaryKey(autoGenerate = true) var uid: Int,
    @ColumnInfo(name = "meal_type") val mealType: String,
    @ColumnInfo(name = "meal_name") val mealName: String?,
    @ColumnInfo(name = "meal_status") val mealStatus: String?,
    @ColumnInfo(name = "meal_greens") var mealGreens: String?,
    @ColumnInfo(name = "meal_grains") var mealGrains: String?,
    @ColumnInfo(name = "meal_proteins") var mealProteins: String?,
    @ColumnInfo(name = "meal_drinks") var mealDrinks: String?,
    @ColumnInfo(name = "meal_day") val mealDay: String?,
    @ColumnInfo(name = "meal_time") val mealTime: String?
)

val LIST_PRJ_STATUS = arrayListOf("OPEN", "CLOSED", "TBD")
val LIST_MEAL_PART = arrayListOf("Chicken", "Beef", "Fish", " Shrimp")
val LIST_DAYS = arrayListOf("MONDAY", "TUESDAY", "WEDNESDAY",
                            "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY")
//val LIST_TIMES = arrayListOf("BREAKFAST", "LUNCH", "DINNER", "SNACK1")

enum class ENUM_MEAL_STATUS { OPEN, CLOSED, TBD}
enum class ENUM_MEAL_TYPES { DEFAULT, CREATED, RECIPE }
enum class ENUM_TIMES { BREAKFAST, SNACK1, LUNCH, SNACK2, DINNER }
enum class ENUM_DAYS { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY}
enum class ENUM_INGREDS
{ GREENS, GRAINS, PROTEINS, DRINKS }

enum class ENUM_GRAINS
{ BREAD, NAAN, DOUGH, RICE }

enum class ENUM_GREENS
{ BROCCOLI, CORN, PEAS, BEANS }

enum class ENUM_PROTEINS
{ CHICKEN, BEEF, EGG, FISH }

enum class ENUM_DRINKS
{ WATER, MILK, JUICE, SMOOTHIE }

fun KClass<out Enum<*>>.enumConstantNames() =
    this.java.enumConstants.map(Enum<*>::name)