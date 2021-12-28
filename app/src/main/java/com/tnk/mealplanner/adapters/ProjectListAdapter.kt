package com.tnk.project_ions.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tnk.project_ions.R
import com.tnk.project_ions.adapters.ProjectListAdapter.ProjectViewHolder
import com.tnk.project_ions.db.Meal

class ProjectListAdapter : ListAdapter<Meal, ProjectViewHolder>(PROJECTS_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        return ProjectViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.mealName)
    }

    class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val projectNameHolder: TextView = itemView.findViewById(R.id.recyclerText)

        fun bind(text: String?) {
            projectNameHolder.text = text
        }

        companion object {
            fun create(parent: ViewGroup): ProjectViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ProjectViewHolder(view)
            }
        }
    }

    companion object {
        private val PROJECTS_COMPARATOR = object : DiffUtil.ItemCallback<Meal>() {
            override fun areItemsTheSame(oldItem: Meal, newItem: Meal): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Meal, newItem: Meal): Boolean {
                return oldItem.mealName == newItem.mealName
            }
        }
    }
}

