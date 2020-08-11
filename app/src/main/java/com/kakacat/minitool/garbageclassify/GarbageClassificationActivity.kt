package com.kakacat.minitool.garbageclassify

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputLayout
import com.kakacat.minitool.R
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.ui.RecycleViewListener
import com.kakacat.minitool.common.ui.view.MyPopupWindow
import com.kakacat.minitool.common.util.UiUtil
import com.kakacat.minitool.common.util.UiUtil.showToast
import com.kakacat.minitool.garbageclassify.model.TypeMap.getTypeName

class GarbageClassificationActivity : BaseActivity(), Contract.View {

    private val presenter: Contract.Presenter by lazy { Presenter(this) }

    private val linearLayout: LinearLayout by lazy { findViewById<LinearLayout>(R.id.linear_layout) }
    private val contentView: View by lazy {
        val view = LayoutInflater.from(this).inflate(R.layout.content_view, linearLayout, false)
        view.tag = ContentDialogHolder(view)
        view
    }
    private val loadingDialog: MyPopupWindow by lazy { MyPopupWindow(this, LayoutInflater.from(this).inflate(R.layout.loading_view, linearLayout, false), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) }
    private val contentDialog: MyPopupWindow by lazy { MyPopupWindow(this, contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT) }
    private val adapter: MyAdapter by lazy { MyAdapter(presenter.garbageList!!) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_garbage_classification)
        initData()
        initView()
    }

    override fun initData() {
        presenter.initData()
    }

    override fun initView() {
        UiUtil.initToolbar(this,true)

        val til = findViewById<TextInputLayout>(R.id.til)
        val editText = findViewById<EditText>(R.id.edit_text)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)

        adapter.setOnClickListener(object : RecycleViewListener.OnItemClick{
            override fun onClick(v: View?, position: Int) {
                showContentView(position)
            }
        })
        til.setStartIconOnClickListener { requestData(editText.text.toString()) }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun requestData(s: String?) {
        loadingDialog.showAtLocation(linearLayout, Gravity.CENTER, 0, 0)
        presenter.requestData(s)
    }

    override fun onRequestCallBack(result: String?) {
        loadingDialog.dismiss()
        adapter.notifyDataSetChanged()
        showToast(this, result)
    }

    @SuppressLint("SetTextI18n")
    override fun showContentView(position: Int) {
        val garbage = presenter.garbageList!![position]
        val type = garbage.type
        val holder: ContentDialogHolder = contentView.tag as ContentDialogHolder

        holder.tvGarbageTitle1.text = getTypeName(type) + "是什么?"
        holder.tvGarbageContent1.text = garbage.explain
        holder.tvGarbageTitle2.text = "有什么常见的" + getTypeName(type) + "?"
        holder.tvGarbageContent2.text = garbage.contain
        holder.tvTip.text = garbage.tip
        contentDialog.showAtLocation(linearLayout, Gravity.BOTTOM, 0, 0)
    }

    internal class ContentDialogHolder(view: View) {
        val tvGarbageTitle1: TextView = view.findViewById(R.id.tv_garbage_type_title1)
        val tvGarbageTitle2: TextView = view.findViewById(R.id.tv_garbage_type_title2)
        val tvGarbageContent1: TextView = view.findViewById(R.id.tv_garbage_type_content1)
        val tvGarbageContent2: TextView = view.findViewById(R.id.tv_garbage_type_content2)
        val tvTip: TextView = view.findViewById(R.id.tv_tip)
    }
}