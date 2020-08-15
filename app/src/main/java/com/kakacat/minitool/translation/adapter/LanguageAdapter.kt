package com.kakacat.minitool.translation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kakacat.minitool.R
import com.kakacat.minitool.common.ui.RecycleViewListener.OnItemClick

class LanguageAdapter(private val languageList: List<String>) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    private var listener: OnItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.language_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvLanguage.text = languageList[position]
        if (listener != null) {
            holder.itemView.setOnClickListener { listener!!.onClick(holder.itemView, position) }
        }
    }

    override fun getItemCount(): Int {
        return languageList.size
    }

    fun setOnClickListener(listener: OnItemClick?) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvLanguage: TextView = itemView as TextView
    }
}