package com.tnk.project_ions

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.view.forEachIndexed

class PrjAddNote:  Activity() {

    // override other abstract methods here
    private val TAG = "Prj_Add"
    private var parentLinearLayout: LinearLayout? = null
    private var thisContext: Context? = null
    //

    override fun onCreate(savedInstanceState: Bundle?) {
        // always
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_add)
        thisContext = this
        title = "Add a Project"
        // For the Dynamic Note List
        parentLinearLayout = findViewById(R.id.layout_dlv_notes)


        val btn_commit = findViewById<Button>(R.id.btn_commit)
        btn_commit.setOnClickListener {
            commitEntry()
        }

        parentLinearLayout!!.setOnClickListener {

            val textView = it.findViewById<EditText>(R.id.number_edit_text)

            Toast.makeText(this, "Clicked " + textView.text.toString(), Toast.LENGTH_SHORT)
        }
    }

    /*
    This FUNCTION deletes the associated View from the UI
    Efforts should be taken to allow this to function for any of the added Views
     */
    fun onDelete(view: View) {
        parentLinearLayout!!.removeView(view.parent as View)
    }

    /*
    Adds a Note field to the UI

    This one is a simple Text Note
     */
    fun onAddField(view: View) {
        val inflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.dlv_field, null)
        parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount - 1)

        Toast.makeText(thisContext, "New Field", Toast.LENGTH_LONG)
    }

    /*
    TODO Update this FUNCTION to add a List to the UI
     */
    fun onAddFieldTest(view: View) {
        val inflater =
            getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView: View = inflater.inflate(R.layout.dlv_field1, null)
        parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount - 1)

        Toast.makeText(thisContext, "New Field Test", Toast.LENGTH_LONG)
    }

    fun commitEntry() {

        val childCount = parentLinearLayout!!.childCount
        Log.v(TAG, "commitEntry :: " + childCount + "children in view parent")

        parentLinearLayout?.forEachIndexed { index, view ->
            if (index > 0 ) {
                val et_test = view.findViewById<EditText>(R.id.number_edit_text)

                Log.v(TAG, "Child msg " + et_test?.text.toString())
            }
        }

        /*

        for (i in 1..(childCount-1)) {
            val childView = parentLinearLayout?.getChildAt(i)
            val et_entry = childView?.findViewById<EditText>(R.id.number_edit_text)

            Log.v(TAG, "Child msg " + et_entry?.text.toString())

            val newCommit = Item_Project(
                0,                                  // ID
                0,                                   // Project Number
                et_entry?.text.toString(),                      // Project Name
                0,                               // Note Number
                "New Note",                          // Note Name
                "TEXT",                          // Note Type
                "OPEN",                             // Note Status
                "WEIGHT",                        // Relation Type
                "MODERATE",                     // Relation Weight
                "N/A",                             // Relation Date
                "This is the body of the note"  // Note Entry
            )
            // Commit that sweet, sweet note
            thisDb?.addProject(newCommit)

            Toast.makeText(thisContext, "This Entry Has Been Committed!", Toast.LENGTH_SHORT)
        }


        for (i in 1..childCount) {
            val view = parentLinearLayout!!.getChildAt(i)
            val textView = view.findViewById<EditText>(R.id.number_edit_text)
            Log.v(TAG, "commitEntry :: " + textView.text.toString())

            val newCommit = Item_Project(
                0,                                  // ID
                0,                                   // Project Number
                "New Project",                      // Project Name
                0,                               // Note Number
                "New Note",                          // Note Name
                "TEXT",                          // Note Type
                "OPEN",                             // Note Status
                "WEIGHT",                        // Relation Type
                "MODERATE",                     // Relation Weight
                "N/A",                             // Relation Date
                "This is the body of the note"  // Note Entry
            )

            // Commit that sweet, sweet note
            //thisDb?.addProject(newCommit)


            Toast.makeText(thisContext, "This Entry Has Been Committed!", Toast.LENGTH_LONG)
        }

         */
    }
}