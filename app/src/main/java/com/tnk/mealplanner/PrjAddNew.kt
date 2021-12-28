package com.tnk.project_ions

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class PrjAddNew : AppCompatActivity() {

    private lateinit var editPrjView: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.content_prjaddnew)
        editPrjView = findViewById(R.id.et_prjadd)

        val button = findViewById<Button>(R.id.btn_prjadd)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editPrjView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            } else {
                val prj = editPrjView.text.toString()
                replyIntent.putExtra(EXTRA_REPLY, prj)
                replyIntent.putExtra(EXTRA_PRJ_STATUS, "OPEN")
                setResult(Activity.RESULT_OK, replyIntent)
            }
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.tnk.project_ions.REPLY"
        const val EXTRA_PRJ_NAME = "com.tnk.project_ions.PRJ_NAME"
        const val EXTRA_PRJ_STATUS = "com.tnk.project_ions.PRJ_STATUS"
        const val EXTRA_NOTE_NAME = "com.tnk.project_ions.NOTE_NAME"
        const val EXTRA_NOTE_TYPE = "com.tnk.project_ions.NOTE_TYPE"
        const val EXTRA_NOTE_STATUS = "com.tnk.project_ions.NOTE_STATUS"
        const val EXTRA_NOTE_BODY = "com.tnk.project_ions.NOTE_BODY"
        const val EXTRA_REL_TYPE = "com.tnk.project_ions.NOTE_STATUS"
        const val EXTRA_REL_WEIGHT = "com.tnk.project_ions.NOTE_STATUS"
        const val EXTRA_REL_DATE = "com.tnk.project_ions.NOTE_STATUS"
    }

}