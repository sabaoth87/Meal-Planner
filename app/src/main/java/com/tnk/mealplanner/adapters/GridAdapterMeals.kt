package com.tnk.project_ions.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.tnk.project_ions.R

internal class GridAdapterMeals(
    private val context: Context,
    private val labelNames: Array<String>,
    private val resImages: IntArray,
    private val tv1: Array<String>,
    private val tv2: Array<String>,
    private val tv3: Array<String>,
    private val tv4: Array<String>
) :
    BaseAdapter() {
    private var layoutInflater: LayoutInflater? = null
    private lateinit var imageView: ImageView
    private lateinit var textView: TextView
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var textView4: TextView

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
            // TODO Convert this view to an appropriate layout
            convertView = layoutInflater!!.inflate(R.layout.grid_item_meal, null)
        }

        imageView = convertView!!.findViewById(R.id.imgView)
        textView = convertView.findViewById(R.id.tv)
        textView1 = convertView.findViewById(R.id.tv1)
        textView2 = convertView.findViewById(R.id.tv2)
        textView3 = convertView.findViewById(R.id.tv3)
        textView4 = convertView.findViewById(R.id.tv4)

        imageView.setImageResource(resImages[position])
        textView.text = labelNames[position]

        textView1.text = tv1[position]
        textView2.text = tv2[position]
        textView3.text = tv3[position]
        textView4.text = tv4[position]

        return convertView
    }

}