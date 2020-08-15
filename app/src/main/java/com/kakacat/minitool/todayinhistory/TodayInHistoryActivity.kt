package com.kakacat.minitool.todayinhistory

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.widget.DatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.kakacat.minitool.R
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.util.UiUtil.initToolbar
import com.kakacat.minitool.common.util.UiUtil.setTranslucentStatusBarWhite
import com.kakacat.minitool.common.util.UiUtil.showToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TodayInHistoryActivity : BaseActivity(), Contract.View {

    private var presenter: Contract.Presenter? = null

    private val datePickerDialog by lazy { DatePickerDialog(this, OnDateSetListener { _: DatePicker?, _: Int, monthOfYear: Int, dayOfMonth: Int ->
        presenter!!.month = monthOfYear + 1
        presenter!!.day = dayOfMonth
        presenter!!.refreshData()
    }, presenter!!.year, presenter!!.month - 1, presenter!!.day) }

    private val adapter by lazy { Adapter(context, R.layout.article_layout, presenter!!.articleList!!) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        initData()
        initView()
    }

    public override fun onResume() {
        super.onResume()
        presenter!!.refreshData()
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
        setTranslucentStatusBarWhite(this)
        initToolbar(this, true)
        val listView = findViewById<MyListView>(R.id.list_view)
        listView.adapter = adapter
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { showCalendarDialog() }
    }

    override fun onUpdateDataCallBack(result: String?, needRefresh: Boolean) {
        GlobalScope.launch(Dispatchers.Main) {
            if (needRefresh) {
                adapter.notifyDataSetChanged()
            }
            showToast(context, result)
        }
    }

    override fun showCalendarDialog() {
        datePickerDialog.show()
    }
}