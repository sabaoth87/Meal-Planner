package com.tnk.project_ions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.tnk.project_ions.adapters.GridAdapterDays
import com.tnk.project_ions.adapters.GridAdapterMeals
import com.tnk.project_ions.db.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MenuMeal : AppCompatActivity() {

    private val tag = "MenuMeal"
    lateinit var mealSelected: String
    lateinit var daySelected: String
    lateinit var tv_meal_selected: TextView
    lateinit var tv_meal_greens: TextView
    lateinit var tv_meal_grains: TextView
    lateinit var tv_meal_proteins: TextView
    lateinit var tv_meal_drinks: TextView
    private var thisMeal: Meal? = null
    private val newPrjActivityRequestCode = 1
    private val mealViewModel: MealViewModel by viewModels {
        MealViewModelFactory((application as MealsApplication).repo)
    }
    //abstract fun MealsDao(): MealsDao

    private val disposable = CompositeDisposable()

    // ELV
    //private var elvUids = HashMap<String, List<Int>>()
    //private var elv: ExpandableListView? = null
    //private var elvAdapter: ELA_Projects? = null
    //private var elvHeadings = ENUM_DAYS::class.enumConstantNames()
    //private var elvData = HashMap<String, List<String>>()

    // GridView
    lateinit var gridView: GridView
    private var ingNames = arrayOf(
        "test",
        "this",
        "to",
        "pieces"
    )
    private var ingImgs = intArrayOf(
        R.drawable.ic_baseline_greens,
        R.drawable.ic_baseline_breakfast_dining_24,
        R.drawable.ic_baseline_egg_24,
        R.drawable.ic_baseline_emoji_food_beverage_24
    )
    //
    private var tv1Strings = Array<String>(5) {"it = $it"}
    private var tv2Strings = Array<String>(5) {"it = $it"}
    private var tv3Strings = Array<String>(5) {"it = $it"}
    private var tv4Strings = Array<String>(5) {"it = $it"}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_grid_meal)

        for (items in ENUM_INGREDS.values()) {
            ingNames[items.ordinal] = items.toString()
        }

        val intent = intent

        daySelected = intent.getStringExtra("DAY").toString()
        mealSelected = intent.getStringExtra("MEAL").toString()

        mealViewModel.allMeals.observe(this) {

            for (meal in it) {
                if (ENUM_DAYS.valueOf(meal.mealDay!!) == ENUM_DAYS.valueOf(daySelected)) {
                    if (ENUM_TIMES.valueOf(meal.mealTime!!) == ENUM_TIMES.valueOf(mealSelected)) {
                        thisMeal = meal
                    }
                }
            }

        }
        tv_meal_selected = findViewById<TextView>(R.id.tv_mealmenu)
        tv_meal_grains = findViewById<TextView>(R.id.tv_meal_grains)
        tv_meal_greens = findViewById<TextView>(R.id.tv_meal_greens)
        tv_meal_proteins = findViewById<TextView>(R.id.tv_meal_proteins)
        tv_meal_drinks = findViewById<TextView>(R.id.tv_meal_drinks)

        disposable.add(
            mealViewModel.testFindFlowByDayTime(daySelected, mealSelected)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    this.tv_meal_selected.text = "Create a beautiful " + mealSelected + "!" +
                            "/n" + it.mealDay.toString()
                    this.thisMeal = it

                    this.tv_meal_grains.text = thisMeal!!.mealGrains.toString()
                    this.tv_meal_greens.text = thisMeal!!.mealGreens.toString()
                    this.tv_meal_proteins.text = thisMeal!!.mealProteins.toString()
                    this.tv_meal_drinks.text = thisMeal!!.mealDrinks.toString()
                },
                    { error -> Log.v(tag, "Unable to get the meal!") })
        )
        //thisMeal = mealViewModel.testFindFlowByDayTime(daySelected, mealSelected)
        //mealViewModel.findByDayTime(daySelected, mealSelected)
        //thisMeal = mealViewModel.foundMeals[0]


        //tv_meal_selected.text = "Create a beautiful " + mealSelected + "!" +
        //       "/n" + thisMeal?.mealDay.toString()

        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            // TODO Initiate the fab for adding different parts of the meal?
            //val intent = Intent(this@MealsMain, PrjAddNew::class.java)
            //startActivityForResult(intent, newPrjActivityRequestCode)
        }

        //val recyclerView = findViewById<RecyclerView>(R.id.recycler_meal_parts)
        //val adapter = ProjectListAdapter()
        //recyclerView.adapter = adapter
        //recyclerView.layoutManager = LinearLayoutManager(this)
        // Add an observer on the LiveData returned by 'one of the get methods'
        // the onChanged() method fires when the observed data changes and the activity is
        // in the foreground
        //mealViewModel.findByDayTime(daySelected,mealSelected).observe(this) { meals ->
        // Update the cached copy of the meals in the adapter
        //    meals.let { adapter.submitList(it) }
        //}

        //setupExpandableListView()

        gridView = findViewById(R.id.grid_meal)
        val gridAdapter = GridAdapterDays(this, ingNames, ingImgs)
        gridView.adapter = gridAdapter
        gridView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(
                applicationContext, "You CLicked " + ingNames[+position],
                Toast.LENGTH_SHORT
            ).show()
            val intent = Intent(this, PromptIngredAdd::class.java)

            val c = enumValues<ENUM_INGREDS>()[position]
            intent.putExtra(PromptIngredAdd.EXTRA_INCOMING, c.name)
            //intent.putExtra(PromptIngredAdd.EXTRA_INCOMING, "1")
            startActivityForResult(intent, newPrjActivityRequestCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == newPrjActivityRequestCode && resultCode == Activity.RESULT_OK) {
            // TODO Expand upon the Extra extractions
            val ingredientName = data!!.getStringExtra(PromptIngredAdd.EXTRA_REPLY)
            val replyType = data?.getStringExtra(PromptIngredAdd.EXTRA_REPLY_TYPE)

            data.getStringExtra(PrjAddNew.EXTRA_REPLY)?.let { reply ->
                Toast.makeText(
                    applicationContext,
                    "Returned a " + replyType + " : " + ingredientName,
                    Toast.LENGTH_LONG
                ).show()
            }

            when (replyType) {
                ENUM_INGREDS.GRAINS.toString() -> {
                    thisMeal!!.mealGrains = ingredientName
                    mealViewModel.updateMealNow(thisMeal!!)
                }
                ENUM_INGREDS.PROTEINS.toString() -> {
                    thisMeal!!.mealProteins = ingredientName
                    mealViewModel.updateMealNow(thisMeal!!)
                }
                ENUM_INGREDS.GREENS.toString() -> {
                    thisMeal!!.mealGreens = ingredientName
                    mealViewModel.updateMealNow(thisMeal!!)
                }
                ENUM_INGREDS.DRINKS.toString() -> {
                    thisMeal!!.mealDrinks = ingredientName
                    mealViewModel.updateMealNow(thisMeal!!)
                }
            }
        } else {
            Toast.makeText(
                applicationContext,
                "No Value To Enter!",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    /**
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
        elv = findViewById(R.id.elv_meal_parts)
        //val data = elvData

        initializeELVdata()

        Log.v(tag, "elvHeadings : " + elvHeadings.size.toString())
        // TODO Convert this ELA to new stream
        elvAdapter = ELA_Projects(this, elvHeadings as ArrayList<String>, elvData)
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

        //pollElvData()
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

                /*
                for (meal in it) {
                    when (meal.mealDay) {
                        ENUM_DAYS.MONDAY.toString() -> {
                            mealsMon.add(meal.mealName!!)
                            monUids.add(meal.uid)
                        }
                        ENUM_DAYS.TUESDAY.toString() -> {
                            mealsTue.add(meal.mealName!!)
                            tueUids.add(meal.uid)
                        }
                        ENUM_DAYS.WEDNESDAY.toString() -> {
                            mealsWed.add(meal.mealName!!)
                            wedUids.add(meal.uid)
                        }
                        ENUM_DAYS.THURSDAY.toString() -> {
                            mealsThu.add(meal.mealName!!)
                            thuUids.add(meal.uid)
                        }
                        ENUM_DAYS.FRIDAY.toString() -> {
                            mealsFri.add(meal.mealName!!)
                            friUids.add(meal.uid)
                        }
                        ENUM_DAYS.SATURDAY.toString() -> {
                            mealsSat.add(meal.mealName!!)
                            satUids.add(meal.uid)
                        }
                        ENUM_DAYS.SUNDAY.toString() -> {
                            mealsSun.add(meal.mealName!!)
                            sunUids.add(meal.uid)
                        }
                    }
                    Log.v(tag, "GREENs :" + mealsMon.size)
                    Log.v(tag, "PROTEINs :" + mealsTue.size)
                    Log.v(tag, "GRAINs :" + mealsWed.size)
                    Log.v(tag, "DRINKs :" + mealsThu.size)
                }

                 */

                elvData.clear()

                elvData[ENUM_INGREDS.GREENS.toString()] = partsGreens
                elvData[ENUM_INGREDS.GRAINS.toString()] = partsGrains
                elvData[ENUM_INGREDS.PROTEINS.toString()] = partsProteins
                elvData[ENUM_INGREDS.DRINKS.toString()] = partsDrinks

                elvUids[ENUM_INGREDS.GREENS.toString()] = greensUids
                elvUids[ENUM_INGREDS.GRAINS.toString()] = grainsUids
                elvUids[ENUM_INGREDS.PROTEINS.toString()] = proteinsUids
                elvUids[ENUM_INGREDS.DRINKS.toString()] = drinksUids
                Log.v(tag, "If statement end")
            }
            Log.v(tag, "If statement done")
            elvAdapter!!.setNewItems(elvHeadings, elvData)
        }

    }
    **/

    companion object {
        const val RETURN_PROTEIN = "com.tnk.PROTEIN"
        const val RETURN_GRAINS = "com.tnk.GRAINS"
        const val RETURN_GREENS = "com.tnk.GREENS"
        const val RETURN_DRINK = "com.tnk.DRINKS"

        const val INCOMING_MEAL = "com.tnk.MEAL_INCOMING"
        const val INCOMING_MEAL_UID = "com.tnk.MEAL_UID"
    }
}