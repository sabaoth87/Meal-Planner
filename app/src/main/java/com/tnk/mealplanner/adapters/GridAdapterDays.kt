package com.tnk.project_ions.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.tnk.project_ions.R

internal class GridAdapterDays (
    private val context: Context,
            private val labelNames: Array<String>,
                    private val resImages: IntArray
) :
        BaseAdapter() {
            private var layoutInflater: LayoutInflater? = null
            private lateinit var imageView: ImageView
                    private lateinit var textView: TextView

    override fun getCount(): Int {
        return labelNames.size
    }

    override fun getItem(position: Int): Any? {
        //TODO("Not yet implemented")
        return null
    }

    override fun getItemId(position: Int): Long {
        //TODO("Not yet implemented")
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var convertView = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (convertView == null) {
            convertView = layoutInflater!!.inflate(R.layout.grid_ing_item, null)
        }

        imageView = convertView!!.findViewById(R.id.imageView)
        textView = convertView!!.findViewById(R.id.textView)
        imageView.setImageResource(resImages[position])
        textView.text = labelNames[position]
        return convertView
    }

        }