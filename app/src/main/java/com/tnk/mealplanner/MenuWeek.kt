package com.tnk.project_ions

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tnk.project_ions.adapters.ELVA_Groceries
import com.tnk.project_ions.adapters.GridAdapterDays
import com.tnk.project_ions.db.*

class MenuWeek : AppCompatActivity() {

    private val tag = "MealsMain"

    private var rawMeals = ArrayList<Meal>()
    // ELV
    private var elvUids = HashMap<String, List<Int>>()
    private var elv: ExpandableListView? = null
    private var elvAdapter: ELVA_Groceries? = null
    private var elvHeadings = ENUM_INGREDS::class.enumConstantNames()
    private var elvData = HashMap<String, List<String>>()
    private var elvDataDays = HashMap<String, List<String>>()
    private var elvDataCounts = HashMap<String, List<Int>>()
    private val mealViewModel: MealViewModel by viewModels {
        MealViewModelFactory((application as MealsApplication).repo)
    }

    // GridView
    lateinit var gridView: GridView
    private var dayNames = arrayOf(
        "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY",
        "SATURDAY", "SUNDAY"
    )
    private var dayImgs = intArrayOf(
        R.drawable.ic_fish,
        R.drawable.ic_fish,
        R.drawable.ic_fish,
        R.drawable.ic_fish,
        R.drawable.ic_fish,
        R.drawable.ic_fish,
        R.drawable.ic_fish
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO The following layout will have to be altered to match the example this is built on
        setContentView(R.layout.activity_grid_week)

        title = "Main View Page"
        val context = this
        //val db = DbHelper_Projects(context)

        mealViewModel.allMeals.observe(this) {
            theseMeals = it
        }

        gridView = findViewById(R.id.grid_week)
        val gridAdapter = GridAdapterDays(this@MenuWeek, dayNames, dayImgs)
        gridView.adapter = gridAdapter
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(
                applicationContext, "You CLicked " + dayNames[+position],
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(this, MenuDay::class.java)
            intent.putExtra("DAY", dayNames[+position])
            startActivity(intent)
        }

        // For the ELV
        setupExpandableListView()
    }

    fun initializeELVdata() {
        val parts = ArrayList<String>()
        val uids = ArrayList<Int>()

        //Log.v(tag, "Building the ELV Data now...")
        //Log.v(tag, "partsMon : " + parts.size.toString())
        for (day in ENUM_DAYS.values()) {
            val meals = mealViewModel.returnListByDay(day.toString())
            parts.clear()
            uids.clear()
            for (meal in meals) {
                parts.add(meal.mealName.toString())
                uids.add(meal.uid)
                //Log.v(tag, "Adding... " + parts.size.toString())
            }
            //Log.v(tag, "Loading to day " + day.toString())
            elvData[day.toString()] = parts
            elvUids[day.toString()] = uids
        }

    }

    fun setupExpandableListView() {
        elv = findViewById(R.id.elv_groceries)
        //val data = elvData

        initializeELVdata()

        Log.v(tag, "elvHeadings : " + elvHeadings.size.toString())

        elvAdapter = ELVA_Groceries(
            this,
            elvHeadings as ArrayList<String>,
            elvData,
            elvDataCounts,
            elvDataDays
        )
        Log.v(tag, "elvData : " + elvData.size.toString())

        elv?.setAdapter(elvAdapter)
        elv?.setOnGroupExpandListener { groupPosition ->
            Toast.makeText(
                applicationContext,
                elvHeadings[groupPosition] + " List Expanded.",
                Toast.LENGTH_SHORT
            ).show()
        }

        elv?.setOnGroupCollapseListener { groupPosition ->
            Toast.makeText(
                applicationContext,
                elvHeadings[groupPosition] + " List Collapsed.",
                Toast.LENGTH_SHORT
            ).show()
        }

        elv?.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            Toast.makeText(
                applicationContext,
                "Clicked: " + elvHeadings[groupPosition] + " -> " + elvData[elvHeadings[groupPosition]]!!.get(
                    childPosition
                ) + " UID: " + elvUids[elvHeadings[groupPosition]]!!.get(childPosition),
                Toast.LENGTH_SHORT
            ).show()
            //Meal_View.intentOptions.setUid(elvUids[elvHeadings[groupPosition]]!!.get(childPosition))
            //val intentToView = Intent(this, Meal_View::class.java)
            //startActivity(intentToView)
            false
        }

        pollElvData()
    }

    fun pollElvData() {
        //elvHeadings = emptyList()

        val partsGreens = ArrayList<String>()
        val partsGrains = ArrayList<String>()
        val partsProteins = ArrayList<String>()
        val partsDrinks = ArrayList<String>()

        val greensUids = ArrayList<Int>()
        val grainsUids = ArrayList<Int>()
        val proteinsUids = ArrayList<Int>()
        val drinksUids = ArrayList<Int>()

        val greensDays = ArrayList<String>()
        val grainsDays = ArrayList<String>()
        val proteinsDays = ArrayList<String>()
        val drinksDays = ArrayList<String>()

        val monMeals = ArrayList<Meal>()
        val tueMeals = ArrayList<Meal>()
        val wedMeals = ArrayList<Meal>()
        val thuMeals = ArrayList<Meal>()
        val friMeals = ArrayList<Meal>()
        val satMeals = ArrayList<Meal>()
        val sunMeals = ArrayList<Meal>()
        val allMeals = HashMap<ENUM_DAYS, List<Meal>>()

        mealViewModel.allMeals.observe(this) {
            if (it != null) {
                Log.v(tag, "Parts not null")
                // Daily Arrays have to be cleared for re-population during app lifecycle
                Log.v(tag, "Clearing meals " + partsGreens.size)
                partsGreens.clear()
                partsGrains.clear()
                partsProteins.clear()
                partsDrinks.clear()
                Log.v(tag, "Cleared meals " + partsGreens.size)

                for (meal in it) {
                    rawMeals.add(meal)
                    when (meal.mealDay) {
                        ENUM_DAYS.MONDAY.toString() -> {
                            monMeals.add(meal)
                            Log.v(tag, "Monday Meal Added!")
                        }
                        ENUM_DAYS.TUESDAY.toString() -> {
                            tueMeals.add(meal)
                            Log.v(tag, "Tuesday Meal Added!")
                        }
                        ENUM_DAYS.WEDNESDAY.toString() -> {
                            wedMeals.add(meal)
                            Log.v(tag, "Wednesday Meal Added!")
                        }
                        ENUM_DAYS.THURSDAY.toString() -> {
                            thuMeals.add(meal)
                            Log.v(tag, "Thursday Meal Added!")
                        }
                        ENUM_DAYS.FRIDAY.toString() -> {
                            friMeals.add(meal)
                            Log.v(tag, "Friday Meal Added!")
                        }
                        ENUM_DAYS.SATURDAY.toString() -> {
                            satMeals.add(meal)
                            Log.v(tag, "Saturday Meal Added!")
                        }
                        ENUM_DAYS.SUNDAY.toString() -> {
                            sunMeals.add(meal)
                            Log.v(tag, "Sunday Meal Added!")
                        }
                    }

                    if (!meal.mealGrains.isNullOrEmpty()) {
                        partsGrains.add(meal.mealGrains.toString())
                        grainsUids.add(meal.uid)
                        grainsDays.add(meal.mealDay.toString().take(1))
                    }
                    if (!meal.mealGreens.isNullOrEmpty()) {
                        partsGreens.add(meal.mealGreens.toString())
                        greensUids.add(meal.uid)
                        greensDays.add(meal.mealDay.toString().take(1))
                    }
                    if (!meal.mealProteins.isNullOrEmpty()) {
                        partsProteins.add(meal.mealProteins.toString())
                        proteinsUids.add(meal.uid)
                        proteinsDays.add(meal.mealDay.toString().take(1))
                    }
                    if (!meal.mealDrinks.isNullOrEmpty()) {
                        partsDrinks.add(meal.mealDrinks.toString())
                        drinksUids.add(meal.uid)
                        drinksDays.add(meal.mealDay.toString().take(1))
                    }
                }
                allMeals[ENUM_DAYS.MONDAY] = monMeals
                allMeals[ENUM_DAYS.TUESDAY] = tueMeals
                allMeals[ENUM_DAYS.WEDNESDAY] = wedMeals
                allMeals[ENUM_DAYS.THURSDAY] = thuMeals
                allMeals[ENUM_DAYS.FRIDAY] = friMeals
                allMeals[ENUM_DAYS.SATURDAY] = satMeals
                allMeals[ENUM_DAYS.SUNDAY] = sunMeals

                examineForDistincts(partsGreens, partsGrains, partsProteins, partsDrinks)
                examineForDayCounts(allMeals)
                // Populate the ExpandableListView data
                //
                elvData.clear()
                elvData[ENUM_INGREDS.GREENS.toString()] = partsGreens.distinct()
                elvData[ENUM_INGREDS.GRAINS.toString()] = partsGrains.distinct()
                elvData[ENUM_INGREDS.PROTEINS.toString()] = partsProteins.distinct()
                elvData[ENUM_INGREDS.DRINKS.toString()] = partsDrinks.distinct()
                //
                elvDataDays.clear()
                elvDataDays[ENUM_INGREDS.GREENS.toString()] = greensDays
                elvDataDays[ENUM_INGREDS.GRAINS.toString()] = grainsDays
                elvDataDays[ENUM_INGREDS.PROTEINS.toString()] = proteinsDays
                elvDataDays[ENUM_INGREDS.DRINKS.toString()] = drinksDays
                //
                elvUids.clear()
                elvUids[ENUM_INGREDS.GREENS.toString()] = greensUids
                elvUids[ENUM_INGREDS.GRAINS.toString()] = grainsUids
                elvUids[ENUM_INGREDS.PROTEINS.toString()] = proteinsUids
                elvUids[ENUM_INGREDS.DRINKS.toString()] = drinksUids
                Log.v(tag, "If statement end")
            }
            Log.v(tag, "If statement done")
            elvAdapter!!.setNewItems(elvHeadings, elvData, elvDataDays, elvDataCounts)
        }

    }

    private fun examineForDayCounts(allMeals: HashMap<ENUM_DAYS, List<Meal>>) {
        for (meals in rawMeals) {
            if (!meals.mealGrains.isNullOrEmpty()) {
                for (parts in ENUM_GRAINS.values())
                when (meals.mealDay) {
                    ENUM_DAYS.MONDAY.toString() -> {

                    }
                }
            }
        }

    }

    fun examineForDistincts(
        partsGreens: ArrayList<String>,
        partsGrains: ArrayList<String>,
        partsProteins: ArrayList<String>,
        partsDrinks: ArrayList<String>
    ) {
        val distinctGreens = partsGreens.distinct()
        val distinctGrains = partsGrains.distinct()
        val distinctProteins = partsProteins.distinct()
        val distinctDrinks = partsDrinks.distinct()

        var disCounter = 0
        //val debug1 = distinctGreens.size
        val greensCounts = ArrayList<Int>()
        val grainsCounts = ArrayList<Int>()
        val proteinsCounts = ArrayList<Int>()
        val drinksCounts = ArrayList<Int>()

        for (distincts in distinctGreens) {
            for (item in partsGreens) {
                if (item == distincts) {
                    disCounter += 1
                }
                val index = distinctGreens.indexOf(distincts)
                //Log.v(tag, "indexOf($distincts) = $index")
                val debug = greensCounts.size
                val debug1 = distinctGreens.size
                //Log.v(tag, "greensCount size $debug distinctGreens size $debug1")
                greensCounts[index] = disCounter
                //Log.v(tag, "Distinct Greens Count $disCounter")
                //Log.v(tag, "$distincts ::: $item")

            }
            disCounter = 0
            //Log.v(tag, "Distinct Greens Count reset")
        }

        for (distincts in distinctGrains) {
            for (item in partsGrains) {
                if (item == distincts) {
                    disCounter += 1
                }
                val index = distinctGrains.indexOf(distincts)
                val debug = grainsCounts.size
                val debug1 = distinctGrains.size
                //Log.v(tag, "grainsCount size $debug distinctGreens size $debug1")
                grainsCounts[index] = disCounter
                //Log.v(tag, "Distinct Grains Count $disCounter")
                //Log.v(tag, "$distincts ::: $item")

            }
            disCounter = 0
            //Log.v(tag, "Distinct Greens Count reset")
        }

        for (distincts in distinctProteins) {
            for (item in partsProteins) {
                if (item == distincts) {
                    disCounter += 1
                }
                val index = distinctProteins.indexOf(distincts)
                //val debug = proteinsCounts.size
                //val debug1 = distinctProteins.size
                //Log.v(tag, "grainsCount size $debug distinctGreens size $debug1")
                proteinsCounts[index] = disCounter
                //Log.v(tag, "Distinct Grains Count $disCounter")
                //Log.v(tag, "$distincts ::: $item")

            }
            disCounter = 0
            //Log.v(tag, "Distinct Greens Count reset")
        }

        for (distincts in distinctDrinks) {
            for (item in partsDrinks) {
                if (item == distincts) {
                    disCounter += 1
                }
                val index = distinctDrinks.indexOf(distincts)
                //val debug = drinksCounts.size
                //val debug1 = distinctDrinks.size
                //Log.v(tag, "grainsCount size $debug distinctGreens size $debug1")
                drinksCounts[index] = disCounter
                //Log.v(tag, "Distinct Grains Count $disCounter")
                //Log.v(tag, "$distincts ::: $item")

            }
            disCounter = 0
            //Log.v(tag, "Distinct Greens Count reset")
        }
        elvDataCounts[ENUM_INGREDS.GREENS.toString()] = greensCounts
        elvDataCounts[ENUM_INGREDS.GRAINS.toString()] = grainsCounts
        elvDataCounts[ENUM_INGREDS.PROTEINS.toString()] = proteinsCounts
        elvDataCounts[ENUM_INGREDS.DRINKS.toString()] = drinksCounts
    }

    companion object {
        private lateinit var theseMeals: List<Meal>
    }
}