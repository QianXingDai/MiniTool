package com.kakacat.minitool.translation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kakacat.minitool.R

class CollectionAdapter(private val stringList: List<String>) : RecyclerView.Adapter<CollectionAdapter.ViewHolder>() {

    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (!this::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        val view = inflater.inflate(R.layout.collection_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = stringList[position]
    }

    override fun getItemCount() = stringList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.tv)
    }
}