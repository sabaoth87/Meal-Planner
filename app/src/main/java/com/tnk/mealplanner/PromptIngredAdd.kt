package com.tnk.project_ions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.tnk.project_ions.adapters.GridAdapterDays
import com.tnk.project_ions.adapters.GridAdapterMeals
import com.tnk.project_ions.db.ENUM_INGREDS

class PromptIngredAdd : AppCompatActivity() {

    private val tag = "Prompt:IngredAdd"

    private lateinit var tvIngredGroup: TextView
    lateinit var groupSelected: String

    // GridView
    private lateinit var grid_ingredients: GridView
    private lateinit var grid_adapter: GridAdapterDays
    private val ingProteins = arrayOf(
        "CHICKEN",
        "BEEF",
        "EGG",
        "FISH"
    )
    private val ingGreens = arrayOf(
        "BROCCOLI",
        "CORN",
        "PEAS",
        "BEANS"
    )
    private val ingGrains = arrayOf(
        "BREAD",
        "NAAN",
        "DOUGH",
        "RICE"
    )
    private val ingDrinks = arrayOf(
        "WATER",
        "MILK",
        "JUICE",
        "SMOOTHIE"
    )
    private var ingWork = arrayOf(
        "THIS","iS","a","Test"
    )
    private val ingError = arrayOf(
        "THERE",
        "WAS",
        "AN",
        "ERROR"
    )
    //
    private var tv1Strings = Array<String>(5) {"it = $it"}
    private var tv2Strings = Array<String>(5) {"it = $it"}
    private var tv3Strings = Array<String>(5) {"it = $it"}
    private var tv4Strings = Array<String>(5) {"it = $it"}
    private var ingImgs = intArrayOf(
        R.drawable.ic_baseline_ramen,
        R.drawable.ic_baseline_burger,
        R.drawable.ic_baseline_egg_24,
        R.drawable.ic_fish
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.con_grid_ingred_add)
        tvIngredGroup = findViewById(R.id.tv_ingredient_add)

        val intent = intent
        groupSelected = intent.getStringExtra(EXTRA_INCOMING).toString()
        tvIngredGroup.text = "Select A " + groupSelected + "!"

        when (groupSelected) {
            ENUM_INGREDS.GRAINS.toString() -> {
                grid_adapter = GridAdapterDays(this, ingGrains, ingImgs)
                ingWork = ingGrains
            }
            ENUM_INGREDS.GREENS.toString() -> {
                grid_adapter = GridAdapterDays(this, ingGreens, ingImgs)
                ingWork = ingGreens
            }
            ENUM_INGREDS.PROTEINS.toString() -> {
                grid_adapter = GridAdapterDays(this, ingProteins, ingImgs)
                ingWork = ingProteins
            }
            ENUM_INGREDS.DRINKS.toString() -> {
                grid_adapter = GridAdapterDays(this, ingDrinks, ingImgs)
                ingWork = ingDrinks
            }
            else -> {
                grid_adapter = GridAdapterDays(this, ingError, ingImgs)
                ingWork = ingError
            }
        }

        grid_ingredients = findViewById(R.id.grid_ingred)

        grid_ingredients.adapter = grid_adapter
        grid_ingredients.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            Toast.makeText(
                applicationContext, "You CLicked " + ingWork[+position],
                Toast.LENGTH_SHORT
            ).show()
            val replyIntent = Intent()
            val ingred = ingWork[+position]
            replyIntent.putExtra(EXTRA_REPLY, ingred)
            replyIntent.putExtra(EXTRA_REPLY_TYPE, groupSelected)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()

        }
    }

    companion object {
        const val EXTRA_REPLY = "com.tnk.project_ions.REPLY"
        const val EXTRA_REPLY_TYPE = "com.tnk.project_ions.REPLY_TYPE"
        const val EXTRA_INCOMING = "com.tnk.project_ions.INCOMING"
    }

}