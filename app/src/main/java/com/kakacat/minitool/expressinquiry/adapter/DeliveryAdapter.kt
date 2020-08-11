package com.kakacat.minitool.expressinquiry.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kakacat.minitool.R
import com.kakacat.minitool.common.ui.RecycleViewListener.OnItemClick
import com.kakacat.minitool.expressinquiry.model.Delivery

class DeliveryAdapter(private val deliveryList: List<Delivery>) : RecyclerView.Adapter<DeliveryAdapter.ViewHolder>() {

    private lateinit var inflater: LayoutInflater
    private var listener: OnItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (!this::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        val view = inflater.inflate(R.layout.delivery_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val delivery = deliveryList[position]
        holder.tvHead.text = delivery.companyName[0].toString()
        holder.tvTitle.text = delivery.companyName + "(" + delivery.getStatus() + ")"
        holder.tvCompanyTel.text = "电话 : " + delivery.tel
        holder.tvUpdateTime.text = delivery.updateTime
        if (listener != null) {
            holder.itemView.setOnClickListener { listener!!.onClick(holder.itemView, position) }
        }
    }

    override fun getItemCount() = deliveryList.size

    fun setOnItemClickListener(listener: OnItemClick) {
        this.listener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHead: TextView = itemView.findViewById(R.id.head_tv)
        val tvTitle: TextView = itemView.findViewById(R.id.title_tv)
        val tvCompanyTel: TextView = itemView.findViewById(R.id.tv_company_tel)
        val tvUpdateTime: TextView = itemView.findViewById(R.id.update_time_tv)
    }
}