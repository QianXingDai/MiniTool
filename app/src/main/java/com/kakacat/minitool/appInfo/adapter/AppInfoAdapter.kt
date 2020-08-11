package com.kakacat.minitool.appInfo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kakacat.minitool.R
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean
import com.kakacat.minitool.common.ui.RecycleViewListener

class AppInfoAdapter(private val appInfoBeanList: List<AppInfoBean>) : RecyclerView.Adapter<AppInfoAdapter.ViewHolder>() {

    private var inflater: LayoutInflater? = null
    private var itemClickListener: RecycleViewListener.OnItemClick? = null

    fun setOnClickListener(itemClickListener: RecycleViewListener.OnItemClick) {
        this.itemClickListener = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val view: View = if (viewType == HEADER_VIEW_TYPE) {
            inflater!!.inflate(R.layout.header_app_info, parent, false)
        } else {
            inflater!!.inflate(R.layout.item_app_info, parent, false)
        }
        return ViewHolder(view, viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position != 0) {
            val appInfoBean = appInfoBeanList[position - 1]
            holder.ivAppIcon!!.setImageDrawable(appInfoBean.icon)
            holder.tvAppName!!.text = appInfoBean.appName
            holder.tvPackageName!!.text = appInfoBean.packageName
            holder.tvAppVersionCode!!.text = appInfoBean.versionName
            holder.tvAppAndroidVersion!!.text = appInfoBean.androidVersionName
            holder.tvAppApiLevel!!.text = appInfoBean.targetSdkVersion.toString()
            if (itemClickListener != null) {
                holder.itemView.setOnClickListener { itemClickListener!!.onClick(holder.itemView, position) }
            }
        }
    }

    override fun getItemCount(): Int {
        //因为有一个headerview
        return appInfoBeanList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) HEADER_VIEW_TYPE else super.getItemViewType(position)
    }

    class ViewHolder(itemView: View, viewType: Int) : RecyclerView.ViewHolder(itemView) {
        var ivAppIcon: ImageView? = null
        var tvAppName: TextView? = null
        var tvPackageName: TextView? = null
        var tvAppVersionCode: TextView? = null
        var tvAppAndroidVersion: TextView? = null
        var tvAppApiLevel: TextView? = null

        init {
            if (viewType != HEADER_VIEW_TYPE) {
                ivAppIcon = itemView.findViewById(R.id.iv_app_icon)
                tvAppName = itemView.findViewById(R.id.tv_app_name)
                tvPackageName = itemView.findViewById(R.id.tv_app_package_name)
                tvAppVersionCode = itemView.findViewById(R.id.tv_app_version_code)
                tvAppAndroidVersion = itemView.findViewById(R.id.tv_app_android_version)
                tvAppApiLevel = itemView.findViewById(R.id.tv_app_api_level)
            }
        }
    }

    companion object {
        private const val HEADER_VIEW_TYPE = 1
    }

}