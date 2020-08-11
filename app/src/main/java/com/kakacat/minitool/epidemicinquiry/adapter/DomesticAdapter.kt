package com.kakacat.minitool.epidemicinquiry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import com.kakacat.minitool.R
import com.kakacat.minitool.common.util.SystemUtil.log
import com.kakacat.minitool.epidemicinquiry.model.GroupBean

class DomesticAdapter(private val groupBeanList: List<GroupBean>) : BaseExpandableListAdapter() {

    private var inflater: LayoutInflater? = null

    override fun getGroupCount(): Int {
        log("getGroupCount" + groupBeanList.size)
        return groupBeanList.size
    }

    override fun getChildrenCount(i: Int): Int {
        log("getChildrenCount" + groupBeanList[i].childBeanList!!.size)
        return groupBeanList[i].childBeanList!!.size
    }

    override fun getGroup(i: Int): Any {
        log("getGroup")
        return groupBeanList[i]
    }

    override fun getChild(i: Int, i1: Int): Any {
        log("getChild")
        return groupBeanList[i].childBeanList!![i1]
    }

    override fun getGroupId(i: Int): Long {
        return i.toLong()
    }

    override fun getChildId(i: Int, i1: Int): Long {
        return i.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(i: Int, b: Boolean, view: View?, viewGroup: ViewGroup): View {
        if(inflater == null){
            inflater = LayoutInflater.from(viewGroup.context)
        }

        var contentView = view
        val holder: GroupViewHolder
        val groupBean = groupBeanList[i]
        if (contentView == null) {
            contentView = inflater!!.inflate(R.layout.group_layout, viewGroup, false)
            holder = GroupViewHolder(contentView)
            contentView.tag = holder
        } else {
            holder = contentView.tag as GroupViewHolder
        }
        holder.btnLocation.text = groupBean.location
        holder.tvCurrentConfirmCount.text = groupBean.currentConfirmCount
        holder.tvSuspectCount.text = groupBean.susPectCount
        holder.tvCuredCount.text = groupBean.curedCount
        holder.tvTotalConfirmCount.text = groupBean.totalConfirmCount
        holder.tvDeadCount.text = groupBean.deadCount
        return contentView!!
    }

    override fun getChildView(i: Int, i1: Int, b: Boolean, view: View?, viewGroup: ViewGroup): View {
        var contentView = view
        val holder: ChildViewHolder
        val childBean = groupBeanList[i].childBeanList!![i1]

        if(contentView == null){
            contentView = inflater!!.inflate(R.layout.child_layout,viewGroup,false)
            holder = ChildViewHolder(contentView)
            contentView.tag = holder
        }else{
            holder = contentView.tag as ChildViewHolder
        }

        holder.tvLocation.text = childBean.cityName
        holder.tvCurrentConfirmCount.text = childBean.currentConfirmedCount
        holder.tvCuredCount.text = childBean.curedCount
        holder.tvSuspectCount.text = childBean.susPectedCount
        holder.tvDeadCount.text = childBean.deadCount
        holder.tvTotalConfirmCount.text = childBean.confirmedCount

        return contentView!!
    }

    override fun isChildSelectable(i: Int, i1: Int): Boolean {
        return true
    }

    internal class GroupViewHolder(itemView: View) {
        val btnLocation: MaterialButton = itemView.findViewById(R.id.btn_location)
        val tvCurrentConfirmCount: TextView = itemView.findViewById(R.id.tv_current_confirm_count)
        val tvTotalConfirmCount: TextView = itemView.findViewById(R.id.tv_total_confirm_count)
        val tvSuspectCount: TextView = itemView.findViewById(R.id.tv_suspect_count)
        val tvCuredCount: TextView = itemView.findViewById(R.id.tv_cured_count)
        val tvDeadCount: TextView = itemView.findViewById(R.id.tv_dead_count)

    }

    internal class ChildViewHolder(itemView: View) {
        val tvLocation: TextView = itemView.findViewById(R.id.tv_location)
        val tvCurrentConfirmCount: TextView = itemView.findViewById(R.id.tv_current_confirm_count)
        val tvTotalConfirmCount: TextView = itemView.findViewById(R.id.tv_total_confirm_count)
        val tvSuspectCount: TextView = itemView.findViewById(R.id.tv_suspect_count)
        val tvCuredCount: TextView = itemView.findViewById(R.id.tv_cured_count)
        val tvDeadCount: TextView = itemView.findViewById(R.id.tv_dead_count)
    }

}