package com.tnk.project_ions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.tnk.project_ions.adapters.ActivityCompanion
import com.tnk.project_ions.adapters.IntentExtraString
import com.tnk.project_ions.db.*
import com.tnk.project_ions.util.Variables

class Meal_View: AppCompatActivity() {

    companion object :ActivityCompanion<IntentOptions>(IntentOptions, Meal_View::class)

    object IntentOptions {
        //private lateinit var context: Context
        lateinit var incomingMeal: Meal
        var mealUid: Int = 0

        //fun setContext(con: Context) {
        //    context=con
        //}

        fun setMeal(intentMeal: Meal) {
            incomingMeal=intentMeal
        }

        fun setUid(uid: Int) {
            mealUid=uid
        }

        var Intent.message by IntentExtraString()
    }

    private val TAG = "Prj_View :: "

    /*
    Shared Vars
     */
    private var extras: Bundle? = null
    private var incomingMeal = false
    private var beginEntrySet = false
    val DATE_TIME_FORMAT = "yyyy-MM-dd kk:mm:ss"
    private var RowId: Long? = null
    private val mealViewModel: MealViewModel by viewModels {
        MealViewModelFactory((application as MealsApplication).repo)
    }
    //TODO Change these to display-only items

    /*
    Module Specific Vars
     */
    private var tv_meal_name: TextView? = null
    private var tv_meal_num: TextView? = null
    /*
    Testing Vars
     */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_card)

        tv_meal_name = findViewById(R.id.meal_card_tv_name)
        tv_meal_num = findViewById(R.id.meal_card_tv_num)

        mealViewModel.findByUid(intentOptions.mealUid)
        incomingMeal = true
        loadMealValues(mealViewModel.foundMeal)

    }

    /*
    TODO UPDATE THE FOLLOWING TO BE CAPABLE WITH ROOM
     */
    private fun setRowIdFromIntent() {
        if (RowId == null) {
            val extras: Bundle? = getIntent().getExtras()
            //val myIntent = Intent(this, Prj_View::class.java)
            // RowId = if (extras != null) extras.getLong(Contract_Project.ProjectEntry._ID) else null
            Toast.makeText(getApplicationContext(), "An _ID was sent to me! + $RowId", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        //PHA_dbHelper!!.close()
    }

    /*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // TODO Create a menu for Projects
        getMenuInflater().inflate(R.menu.tb_menu_prj_edit, menu)
        return true
    }
    */

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.menu_item_prj_commit) {
            //saveState()
            setResult(Activity.RESULT_OK)
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        //setRowIdFromIntent();
        //populateFields()
    }

    private fun populateFields() {
        if (incomingMeal) {
            /*
            This is the area to populate fields that is generated from the Intent package
             */
            //tv_prjname!!.setText(newString)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        /*
         @FIX_ME __ - onSaveInstanceState
         Attempting to stash the RowId of the current RowId, so we can try to persist
          */
        outState.putLong("Contract_Project.ProjectEntry._ID", RowId!!)
    }

    fun loadMealValues(meal: Meal) {
        //load the Project information into the UI here
        //fab_add.setEnabled(false);
        if (Variables.mode_debug) {
            Log.v(TAG, "Trying to load Editable Project...")
        }
        tv_meal_num!!.text = meal.uid.toString()
        tv_meal_name!!.text = meal.mealName


        beginEntrySet = false
    }
}
