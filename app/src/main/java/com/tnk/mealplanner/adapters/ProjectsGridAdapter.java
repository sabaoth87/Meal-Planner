package com.tnk.project_ions.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tnk.project_ions.R;
import com.tnk.project_ions.db.Meal;

import java.util.List;

public class ProjectsGridAdapter extends BaseAdapter {

    private final Context tContext;
    private final List<Meal> meals;
    private LayoutInflater layoutInflater;

    public ProjectsGridAdapter(Context newContext, List<Meal> newProjects){
        this.tContext = newContext;
        this.meals = newProjects;
        layoutInflater = LayoutInflater.from(tContext);
    }

    @Override
    public int getCount(){
        return meals.size();
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return meals.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();

            convertView = layoutInflater.inflate(R.layout.grid_item_project, null);
            holder.ablView = (TextView) convertView.findViewById(R.id.gitem_projectABL);
            holder.shortView = (TextView) convertView.findViewById(R.id.gitem_projectShort);
            holder.progressView = (TextView) convertView.findViewById(R.id.gitem_projectProgress);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Meal project = this.meals.get(position);
        holder.ablView.setText(String.valueOf(project.getMealName()));
        holder.shortView.setText(project.getMealTime());
        //holder.progressView.setText(String.valueOf(project.getProjectProgress()));

        // @FIX_ME Attempting to cast; lowered API.
        int prog = 30;

        if (0 <= prog && prog <= 33) {
            holder.progressView.setBackgroundColor(Color.RED);
        } else if (33 < prog && prog <= 66) {
            holder.progressView.setBackgroundColor(Color.YELLOW);
        } else if (prog > 66) {
            holder.progressView.setBackgroundColor(Color.GREEN);
        }

        return convertView;
    }

    static class ViewHolder {
        TextView ablView;
        TextView shortView;
        TextView progressView;
    }
}
