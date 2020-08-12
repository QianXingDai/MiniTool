package com.kakacat.minitool.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kakacat.minitool.R
import com.kakacat.minitool.common.ui.RecycleViewListener.OnItemClick
import com.kakacat.minitool.main.model.AboutItem

class AboutItemAdapter(private val list: List<AboutItem>) : RecyclerView.Adapter<AboutItemAdapter.ViewHolder>() {

    private var clickListener: OnItemClick? = null
    private lateinit var layoutInflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (!this::layoutInflater.isInitialized) {
            layoutInflater = LayoutInflater.from(parent.context)
        }
        val view = layoutInflater.inflate(R.layout.about_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val aboutItem = list[position]
        holder.imageView.setImageResource(aboutItem.iconId)
        holder.textView.text = aboutItem.title
        if (clickListener != null) {
            holder.itemView.setOnClickListener { clickListener!!.onClick(holder.itemView, position) }
        }
    }

    override fun getItemCount() = list.size

    fun setOnClickListener(listener: OnItemClick?) {
        clickListener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.iv_icon)
        val textView: TextView = itemView.findViewById(R.id.tv_title)
    }
}