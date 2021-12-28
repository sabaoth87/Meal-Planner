package com.tnk.project_ions.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.tnk.project_ions.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ELVA_Groceries extends BaseExpandableListAdapter {


    private Context _context;
    private List<String> _listDataHeader;                           // Entry Headings

    private HashMap<String, List<String>> _listDataChildNames;      // Entry Child(names)
    private HashMap<String, List<Integer>> _listDataChildAmounts;   // Entry Child(amount)
    private HashMap<String, List<String>> _listDataChildLocs;       // Entry Child(locations)
    private HashMap<String, List<String>> _listDataChildDays;       // Entry Child(days)

    public ELVA_Groceries(Context context, List<String> groceryHeaders,
                          HashMap<String, List<String>> groceryChildData,
                          HashMap<String, List<Integer>> groceryChildDataCounts,
                          HashMap<String, List<String>> groceryChildDataDays
                            )
    {
        this._context = context;
        this._listDataHeader = groceryHeaders;
        this._listDataChildNames = groceryChildData;
        this._listDataChildAmounts = groceryChildDataCounts;
        this._listDataChildDays = groceryChildDataDays;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        final ArrayList<String> returnArray = new ArrayList<String>();

        returnArray.add(this._listDataChildNames.get(this._listDataHeader.get(groupPosition))
                .get(childPosition));
        returnArray.add(this._listDataChildDays.get(this._listDataHeader.get(groupPosition))
                .get(childPosition));
        // List<Integer> debug = this._listDataChildAmounts.get(this._listDataHeader.get(groupPosition));
        //returnArray.add(String.valueOf(debug.get(childPosititon+1)));
        Log.v("ELVA_GROC", "child " + childPosition + " group " + groupPosition);
        returnArray.add(this._listDataChildAmounts.get(this._listDataHeader.get(groupPosition))
                .get(childPosition).toString());

        return returnArray;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        //final String childText = (String) getChild(groupPosition, childPosition);

        final ArrayList<String> childTexts = (ArrayList<String>) getChild(groupPosition, childPosition);


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group_item_groceries, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lgitem_grocery_name);

        TextView txtListChildDays = (TextView) convertView
                .findViewById(R.id.lgitem_grocery_location);

        TextView txtListChildAmounts = (TextView) convertView
                .findViewById(R.id.lgitem_grocery_amount);

        txtListChild.setText(childTexts.get(0));
        txtListChildDays.setText(childTexts.get(1));
        txtListChildAmounts.setText(childTexts.get(2));
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        // TODO No children found!
        // TODO Investigate null entry placeholders
        return this._listDataChildNames.get(this._listDataHeader.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group_groceries, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lgheader_groceries);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setNewItems(List<String> listDataHeader,
                            HashMap<String, List<String>> listChildData,
                            HashMap<String, List<String>> listChildDataDays,
                            HashMap<String, List<Integer>> listDataChildCounts) {
        this._listDataHeader = listDataHeader;
        this._listDataChildNames = listChildData;
        this._listDataChildAmounts = listDataChildCounts;
        this._listDataChildDays = listChildDataDays;
        notifyDataSetChanged();
    }

}
