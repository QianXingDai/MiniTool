package com.kakacat.minitool.appInfo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.kakacat.minitool.R
import com.kakacat.minitool.appInfo.model.bean.ApiPercentBean

class ApiPercentAdapter(private val list : List<ApiPercentBean?>) : RecyclerView.Adapter<ApiPercentAdapter.ViewHolder>(){

    private var inflater : LayoutInflater? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(inflater == null){
            inflater = LayoutInflater.from(parent.context)
        }
        val view: View = if (viewType == HEADER_VIEW_TYPE){
            inflater!!.inflate(R.layout.header_api_percent,parent,false)
        }else{
            inflater!!.inflate(R.layout.item_api_percent,parent,false)
        }
        return ViewHolder(view,viewType)
    }

    override fun getItemCount() = list.size + 1

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(position != 0){
            val bean = list[position - 1]
            if(bean != null){
                holder.sdvAndroidIcon!!.setActualImageResource(bean.iconId)
                holder.tvAndroidVersionName!!.text = bean.versionName
                holder.tvApiLevel!!.text = bean.versionApi
                holder.tvAppNum!!.text = bean.appNum.toString()
                holder.tvAppPercent!!.text = bean.appPercent
            }
        }
    }

    override fun getItemViewType(position: Int) = if(position == 0){
        HEADER_VIEW_TYPE
    }else{
        super.getItemViewType(position)
    }

    class ViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        var sdvAndroidIcon: SimpleDraweeView? = null
        var tvAndroidVersionName: TextView? = null
        var tvApiLevel: TextView? = null
        var tvAppNum: TextView? = null
        var tvAppPercent: TextView? = null

        init {
            if (viewType != HEADER_VIEW_TYPE) {
                sdvAndroidIcon = itemView.findViewById(R.id.iv_android_logo)
                tvAndroidVersionName = itemView.findViewById(R.id.tv_android_version_name)
                tvApiLevel = itemView.findViewById(R.id.tv_api_level)
                tvAppNum = itemView.findViewById(R.id.tv_app_num)
                tvAppPercent = itemView.findViewById(R.id.tv_app_percent)
            }
        }
    }

    companion object {
        private const val HEADER_VIEW_TYPE = 1
    }

}