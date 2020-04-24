package com.example.heroesapp_v1.front.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.heroesapp_v1.R


class ExpandableListAdapter( private val context: Context,
                             private var title: MutableList<String>,
                             private var body: MutableList<MutableList<String>>
): BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): String {
        return title[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return false
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {

        var groupView = convertView
        if (groupView == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            groupView = inflater.inflate(R.layout.expandable_list_group, null)
        }
        val title  = groupView?.findViewById<TextView>(R.id.text_view_group_title)
        title?.text = getGroup(groupPosition)
        return groupView
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return body[groupPosition].size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): String {
        return body[groupPosition][childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View? {

        var childView = convertView
        if (childView == null){
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            childView = inflater.inflate(R.layout.expandable_list_child, null)
        }
        val title  = childView?.findViewById<TextView>(R.id.text_view_child_title)
        title?.text = getChild(groupPosition, childPosition)
        return childView
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return  childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return title.size
    }
}