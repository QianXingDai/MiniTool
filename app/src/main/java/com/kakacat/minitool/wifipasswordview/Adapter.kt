package com.kakacat.minitool.wifipasswordview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kakacat.minitool.R
import com.kakacat.minitool.common.ui.RecycleViewListener.OnItemLongClick
import com.kakacat.minitool.wifipasswordview.model.Wifi

class Adapter(private val wifiList: List<Wifi>) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    private lateinit var inflater: LayoutInflater
    private var longClickListener: OnItemLongClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (!this::inflater.isInitialized) {
            inflater = LayoutInflater.from(parent.context)
        }
        val view = inflater.inflate(R.layout.wifi_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val wifi = wifiList[position]
        holder.tvHead.text = wifi.wifiImage
        holder.tvWifiName.text = wifi.wifiName
        holder.tvWifiPwd.text = wifi.wifiPwd
        if (longClickListener != null) {
            holder.itemView.setOnLongClickListener { v: View? ->
                longClickListener!!.onLongClick(v, position)
                false
            }
        }
    }

    override fun getItemCount() = wifiList.size

    fun setLongClickListener(longClickListener: OnItemLongClick) {
        this.longClickListener = longClickListener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvHead: TextView = itemView.findViewById(R.id.tv_head)
        val tvWifiName: TextView = itemView.findViewById(R.id.tv_wifi_name)
        val tvWifiPwd: TextView = itemView.findViewById(R.id.tv_wifi_pwd)
    }
}