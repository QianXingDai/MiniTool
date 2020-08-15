package com.kakacat.minitool.cleanfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.kakacat.minitool.R
import com.kakacat.minitool.cleanfile.adapter.FileAdapter
import com.kakacat.minitool.cleanfile.model.FileItem
import com.kakacat.minitool.common.ui.RecycleViewListener

class MyFragment(private val fileItemList: MutableList<FileItem>) : Fragment() {

    val adapter by lazy { FileAdapter(fileItemList) }
    var isSelectedAll = false
        private set

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_layout, container, false)
        val rv: RecyclerView = view.findViewById(R.id.rv_file)
        adapter.setOnClickListener(object : RecycleViewListener.OnItemClick {
             override fun onClick(v: View?, position: Int) {
                 val fileItem = fileItemList[position]
                 val checkBox = v!!.findViewById<CheckBox>(R.id.cb_selected)
                 if (fileItem.checked) {
                     checkBox.isChecked = false
                     fileItem.checked = false
                 } else {
                     checkBox.isChecked = true
                     fileItem.checked = true
                 }
             }
         })
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        return view
    }

    fun setSelectedAll(selectedAll: Boolean, btn: MaterialButton) {
        isSelectedAll = selectedAll
        if (isSelectedAll) {
            btn.setBackgroundResource(R.drawable.ic_clear)
        } else {
            btn.setBackgroundResource(R.drawable.ic_select_all)
        }
    }
}