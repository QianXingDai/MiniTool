package com.kakacat.minitool.expressinquiry.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kakacat.minitool.R
import com.kakacat.minitool.expressinquiry.model.PackageDetail

class DeliveryDetailAdapter(private val packageDetailList: List<PackageDetail>?) : RecyclerView.Adapter<DeliveryDetailAdapter.ViewHolder>() {

    private lateinit var context: Context
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (!this::inflater.isInitialized) {
            context = parent.context
            inflater = LayoutInflater.from(context)
        }
        val view: View = if (viewType == HEAD_VIEW) {
            inflater.inflate(R.layout.delivery_detail_head, parent, false)
        } else {
            inflater.inflate(R.layout.delivery_detail_item, parent, false)
        }
        return ViewHolder(view, viewType)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder.isHeadView) {
            holder.tvCostTime!!.text = "5678"
        } else {
            val packageDetail = packageDetailList!![position]
            val times = packageDetail.time.split(" ".toRegex()).toTypedArray()
            holder.tvUpdateTime1!!.text = times[1]
            holder.tvUpdateTime2!!.text = times[0]
            holder.tvDetail!!.text = packageDetail.content
            if (position == 1) {
                val textColor = context.getColor(R.color.colorAccent)
                holder.tvUpdateTime1!!.setTextColor(textColor)
                holder.tvUpdateTime2!!.setTextColor(textColor)
                holder.tvDetail!!.setTextColor(textColor)
            }
        }
    }

    override fun getItemCount(): Int {
        return packageDetailList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            HEAD_VIEW
        } else super.getItemViewType(position)
    }

    class ViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        var tvUpdateTime1: TextView? = null
        var tvUpdateTime2: TextView? = null
        var tvDetail: TextView? = null
        var isHeadView = false
        var tvCostTime: TextView? = null

        init {
            if (viewType == HEAD_VIEW) {
                isHeadView = true
                tvCostTime = itemView.findViewById(R.id.tv_cost_time)
            } else {
                isHeadView = false
                tvUpdateTime1 = itemView.findViewById(R.id.tv_update_time1)
                tvUpdateTime2 = itemView.findViewById(R.id.tv_update_time2)
                tvDetail = itemView.findViewById(R.id.tv_content)
            }
        }
    }

    companion object {
        private const val HEAD_VIEW = 1
    }
}