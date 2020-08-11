package com.kakacat.minitool.bingpic

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import com.kakacat.minitool.R
import com.kakacat.minitool.common.ui.RecycleViewListener


class ImageAdapter(private val addressList: List<String?>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    private var inflater : LayoutInflater? = null
    private var onItemClickListener: RecycleViewListener.OnItemClick? = null
    private var onLongClickListener: RecycleViewListener.OnItemLongClick? = null
    private var onTouchListener: RecycleViewListener.OnTouch? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if(inflater == null){
            inflater = LayoutInflater.from(parent.context)
        }
        return ViewHolder(inflater!!.inflate(R.layout.bing_pic_item_layout,parent,false))
    }

    override fun getItemCount() = addressList.size

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.sdv!!.setImageURI(addressList[position])
        if(onItemClickListener != null){
            holder.sdv!!.setOnClickListener{ onItemClickListener!!.onClick(holder.itemView,position)}
        }
        if(onLongClickListener != null){
            holder.sdv!!.setOnLongClickListener{
                onLongClickListener!!.onLongClick(holder.sdv,position)
                false
            }
        }
        if(onTouchListener != null){
            holder.sdv!!.setOnTouchListener { v, event ->
                onTouchListener!!.onTouch(v,event)
                false
            }
        }
    }

    fun setOnClickListener(listener: RecycleViewListener.OnItemClick){
        this.onItemClickListener = listener
    }

    fun setOnLongClickListener(listener: RecycleViewListener.OnItemLongClick) {
        this.onLongClickListener = listener
    }

    fun setOnTouchListener(listener: RecycleViewListener.OnTouch) {
        this.onTouchListener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var sdv : SimpleDraweeView? = null

        init {
            this.sdv = itemView.findViewById(R.id.image_view)
        }
    }
}