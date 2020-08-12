package com.kakacat.minitool.cleanfile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.kakacat.minitool.R
import com.kakacat.minitool.cleanfile.model.FileItem
import com.kakacat.minitool.common.ui.RecycleViewListener
import com.kakacat.minitool.common.util.StringUtil

class FileAdapter(private val fileList: List<FileItem>) : RecyclerView.Adapter<FileAdapter.ViewHolder>() {

    private var inflater: LayoutInflater? = null
    private var onClickListener: RecycleViewListener.OnItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val view = inflater!!.inflate(R.layout.file_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fileItem = fileList[position]
        if(fileItem.file != null && fileItem.file!!.exists()){
            val result = StringUtil.byteToMegabyte(fileItem.file!!.length())
            holder.tvFileSize.text = result
            holder.tvFileName.text = fileItem.file!!.name
            holder.tvFilePath.text = fileItem.file!!.absolutePath
            holder.checkBox.isChecked = fileItem.checked
            holder.btFileDetail.setOnClickListener { /*
                 * 这次懒得写..下次再写这里...
                 *
                 * */
            }
            if (onClickListener != null) {
                holder.itemView.setOnClickListener { onClickListener!!.onClick(holder.itemView, position) }
            }
        }

    }

    override fun getItemCount() = fileList.size

    fun setOnClickListener(listener: RecycleViewListener.OnItemClick?) {
        this.onClickListener = listener
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFileSize: TextView = itemView.findViewById(R.id.tv_head)
        val tvFileName: TextView = itemView.findViewById(R.id.tv_file_name)
        val tvFilePath: TextView = itemView.findViewById(R.id.tv_file_path)
        val checkBox: CheckBox = itemView.findViewById(R.id.cb_selected)
        val btFileDetail: MaterialButton = itemView.findViewById(R.id.bt_file_detail)
    }
}