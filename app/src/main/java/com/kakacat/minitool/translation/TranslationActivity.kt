package com.kakacat.minitool.translation

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakacat.minitool.R
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.ui.RecycleViewListener.OnItemClick
import com.kakacat.minitool.common.ui.view.MyPopupWindow
import com.kakacat.minitool.common.util.SystemUtil.copyToClipboard
import com.kakacat.minitool.common.util.UiUtil
import com.kakacat.minitool.common.util.UiUtil.showSnackBar
import com.kakacat.minitool.common.util.UiUtil.showToast
import com.kakacat.minitool.translation.adapter.CollectionAdapter
import com.kakacat.minitool.translation.adapter.LanguageAdapter

class TranslationActivity : BaseActivity(), Contract.View {

    private var presenter: Contract.Presenter? = null

    private val linearLayout by lazy { findViewById<LinearLayout>(R.id.linear_layout) }
    private val editText by lazy { findViewById<EditText>(R.id.edit_text) }
    private val tvOutput by lazy { findViewById<TextView>(R.id.tv_output) }
    private val tvFrom by lazy{ findViewById<TextView>(R.id.tv_output) }
    private val tvTo by lazy { findViewById<TextView>(R.id.tv_to) }
    private val collectionDialog by lazy {
        val view = inflater.inflate(R.layout.collection_layout, linearLayout, false)
        val collectionDialog = MyPopupWindow(this, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val rv: RecyclerView = view.findViewById(R.id.rv)
        rv.adapter = collectionAdapter
        rv.layoutManager = LinearLayoutManager(this)
        collectionDialog
    }
    private val selectLanguageDialog1 by lazy {
        val view = inflater!!.inflate(R.layout.select_from_window, linearLayout, false)
        val selectLanguageDialog = MyPopupWindow(this, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val rv: RecyclerView = view.findViewById(R.id.rv_from)
        val languageAdapter = LanguageAdapter(presenter!!.languageList1)
        languageAdapter.setOnClickListener(object : OnItemClick {
            override fun onClick(v: View?, position: Int) {
                tvFrom!!.text = presenter!!.languageList1[position]
                selectLanguageDialog.dismiss()
            }
        })
        rv.adapter = languageAdapter
        rv.layoutManager = LinearLayoutManager(this)
        selectLanguageDialog
    }
    private val selectLanguageDialog2 by lazy {
        val view = inflater!!.inflate(R.layout.select_from_window, linearLayout, false)
        val selectLanguageDialog = MyPopupWindow(this, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val rv: RecyclerView = view.findViewById(R.id.rv_from)
        val languageAdapter = LanguageAdapter(presenter!!.languageList2)
        languageAdapter.setOnClickListener(object : OnItemClick {
            override fun onClick(v: View?, position: Int) {
                tvTo!!.text = presenter!!.languageList2[position]
                selectLanguageDialog.dismiss()
            }
        })
        rv.adapter = languageAdapter
        rv.layoutManager = LinearLayoutManager(this)
        selectLanguageDialog
    }
    private val inflater by lazy { LayoutInflater.from(this) }
    private val collectionAdapter by lazy { CollectionAdapter(presenter!!.collectionList) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translation)
        initData()
        initView()
    }

    override fun onDestroy() {
        presenter = null
        super.onDestroy()
    }

    override fun initData() {
        presenter = Presenter(this)
        presenter!!.initData()
    }

    override fun initView() {
        UiUtil.initToolbar(this)
    }

    override fun showCollectionWindow() {
        collectionDialog.showAtLocation(linearLayout, Gravity.BOTTOM, 0, 0)
    }

    override fun onAddToMyFavouriteCallBack(s: String?) {
        showSnackBar(linearLayout, s)
        collectionAdapter.notifyDataSetChanged()
    }

    override fun showSelectLanguageWindow(flag: Int) {
        if (flag == 1) {
            selectLanguageDialog1.showAtLocation(linearLayout, Gravity.CENTER, 0, 0)
        } else if (flag == 2) {
            selectLanguageDialog2.showAtLocation(linearLayout, Gravity.CENTER, 0, 0)
        }
    }

    override fun onRequestCallBack(result: String?, warn: String?) {
        if (!TextUtils.isEmpty(result)) {
            tvOutput.text = result
        }
        showToast(this, warn)
    }

    fun onClick(v: View) {
        when (v.id) {
            R.id.iv_open_collect -> {
                showCollectionWindow()
            }
            R.id.iv_clear -> {
                editText.setText("")
            }
            R.id.tv_from -> {
                showSelectLanguageWindow(1)
            }
            R.id.tv_to -> {
                showSelectLanguageWindow(2)
            }
            R.id.fab -> {
                presenter!!.requestData(editText.text.toString(), tvFrom.text, tvTo.text)
            }
            R.id.iv_copy -> {
                copyToClipboard(this, "translate", tvOutput!!.text)
                showSnackBar(linearLayout, "复制完成")
            }
            R.id.iv_collect -> {
                presenter!!.addToMyFavourite(editText.text.toString(), tvOutput.text.toString())
            }
            R.id.bt_back -> {
                if (selectLanguageDialog1.isShowing) {
                    selectLanguageDialog1.dismiss()
                }
                if (selectLanguageDialog2.isShowing) {
                    selectLanguageDialog2.dismiss()
                }
            }
        }
    }
}