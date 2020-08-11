package com.kakacat.minitool.garbageclassify

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kakacat.minitool.R
import com.kakacat.minitool.common.ui.RecycleViewListener.OnItemClick
import com.kakacat.minitool.garbageclassify.model.Garbage
import com.kakacat.minitool.garbageclassify.model.TypeMap.getIcon
import com.kakacat.minitool.garbageclassify.model.TypeMap.getTypeName

class MyAdapter(private val garbageList: List<Garbage>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private var listener: OnItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.garbage_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val garbage = garbageList[position]
        val type = garbage.type
        holder.tvName.text = garbage.name
        holder.tvType.text = getTypeName(type)
        holder.ivIcon.setBackgroundResource(getIcon(type))
        if (listener != null) {
            holder.itemView.setOnClickListener { listener!!.onClick(holder.itemView, position) }
        }
    }

    override fun getItemCount(): Int {
        return garbageList.size
    }

    fun setOnClickListener(listener: OnItemClick?) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivIcon: ImageView = itemView.findViewById(R.id.iv_icon)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvType: TextView = itemView.findViewById(R.id.tv_type)
    }
}