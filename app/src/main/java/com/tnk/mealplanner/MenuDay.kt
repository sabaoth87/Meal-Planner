package com.tnk.project_ions

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tnk.project_ions.adapters.GridAdapterDays
import com.tnk.project_ions.adapters.GridAdapterMeals
import com.tnk.project_ions.db.*

class MenuDay : AppCompatActivity() {

    private val tag = "MenuDay"
    lateinit var daySelected: String
    private lateinit var gridAdapter: GridAdapterMeals
    private val mealViewModel: MealViewModel by viewModels {
        MealViewModelFactory((application as MealsApplication).repo)
    }
    // GridView
    lateinit var gridView: GridView
    private var mealNames = arrayOf(
        "BREAKFAST",
        "SNACK 1",
        "LUNCH",
        "SNACK 2",
        "DINNER")
    private var mealImgs = intArrayOf(
        R.drawable.ic_baseline_brightness_low_24,
        R.drawable.ic_baseline_broken_image_24,
        R.drawable.ic_baseline_brightness_4_24,
        R.drawable.ic_baseline_broken_image_24,
        R.drawable.ic_baseline_brightness_high_24)
    //
    private var tv1Strings = Array<String>(5) {"some"}
    private var tv2Strings = Array<String>(5) {"thing"}
    private var tv3Strings = Array<String>(5) {"didnt"}
    private var tv4Strings = Array<String>(5) {"work"}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Attach to the main layout
        setContentView(R.layout.activity_grid_day)

        for (items in ENUM_TIMES.values()) {
            mealNames[items.ordinal] = items.toString()
        }

        val intent = intent
        daySelected = intent.getStringExtra("DAY").toString()

        val tv_day_selected = findViewById<TextView>(R.id.tv_daymenu)
        tv_day_selected.text = daySelected

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
        // TODO Initiate the fab for adding different parts of the meal?
        //val intent = Intent(this@MealsMain, PrjAddNew::class.java)
            //startActivityForResult(intent, newPrjActivityRequestCode)
        }

        mealViewModel.allMeals.observe(this) {

            for (meal in it) {
                when (meal.mealDay) {
                    daySelected -> {
                        tv1Strings[ENUM_TIMES.valueOf(meal.mealTime.toString()).ordinal] = meal.mealGrains.toString()
                        tv2Strings[ENUM_TIMES.valueOf(meal.mealTime.toString()).ordinal] = meal.mealGreens.toString()
                        tv3Strings[ENUM_TIMES.valueOf(meal.mealTime.toString()).ordinal] = meal.mealProteins.toString()
                        tv4Strings[ENUM_TIMES.valueOf(meal.mealTime.toString()).ordinal] = meal.mealDrinks.toString()
                        Log.v(tag, "Putting " + meal.mealGrains.toString() + " in spot " + ENUM_TIMES.valueOf(meal.mealTime.toString()).ordinal)
                        Log.v(tag, "RPT " + tv1Strings[ENUM_TIMES.valueOf(meal.mealTime.toString()).ordinal])
                        gridAdapter.notifyDataSetChanged()
                    }

                }
            }
        }

        gridView = findViewById(R.id.grid_day)
        gridAdapter = GridAdapterMeals(this, mealNames, mealImgs,
        tv1Strings, tv2Strings, tv3Strings, tv4Strings)
        gridView.adapter = gridAdapter
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(
                applicationContext, "You CLicked " + mealNames[+position],
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(this, MenuMeal::class.java)
            intent.putExtra("MEAL", mealNames[+position])
            intent.putExtra("DAY", daySelected)
            startActivity(intent)
        }

    }
}