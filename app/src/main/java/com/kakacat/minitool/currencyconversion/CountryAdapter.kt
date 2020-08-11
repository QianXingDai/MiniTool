package com.kakacat.minitool.currencyconversion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.kakacat.minitool.R
import com.kakacat.minitool.common.ui.RecycleViewListener
import com.kakacat.minitool.currencyconversion.model.CountryBean

class CountryAdapter(private val countryBeanList: List<CountryBean>) : RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    private var inflater: LayoutInflater?= null

    private var listener: RecycleViewListener.OnItemClick? = null

    fun setOnClickListener(listener: RecycleViewListener.OnItemClick) {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val view = inflater!!.inflate(R.layout.item_country_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val countryBean = countryBeanList[position]
        holder.imageView.setActualImageResource(countryBean.iconId)
        holder.textView.setText(countryBean.unitId)
        if (listener != null) {
            holder.itemView.setOnClickListener { listener!!.onClick(holder.itemView, position) }
        }
    }

    override fun getItemCount(): Int {
        return countryBeanList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: SimpleDraweeView = itemView.findViewById(R.id.iv_country)
        val textView: TextView = itemView.findViewById(R.id.tv_country_name)
    }
}