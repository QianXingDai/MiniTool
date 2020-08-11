package com.kakacat.minitool.expressinquiry.activity

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.kakacat.minitool.R
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.util.UiUtil
import com.kakacat.minitool.expressinquiry.adapter.DeliveryDetailAdapter
import com.kakacat.minitool.expressinquiry.model.Delivery

class DeliveryDetailActivity : BaseActivity() {

    private var mDelivery: Delivery? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_detail)
        initView()
    }

    private fun initView() {
        UiUtil.setTranslucentStatusBarBlack(this)
        mDelivery = intent.getSerializableExtra("delivery") as Delivery
        initToolbar()
        val recyclerView = findViewById<RecyclerView>(R.id.rv)
        val adapter = DeliveryDetailAdapter(mDelivery!!.packageDetailList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun initToolbar() {
        setSupportActionBar(findViewById(R.id.toolbar))
        val toolbarLayout = findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)
        toolbarLayout.title = mDelivery!!.companyName
        toolbarLayout.setCollapsedTitleTextColor(Color.WHITE)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back)
            actionBar.setDisplayShowTitleEnabled(true)
            actionBar.setDisplayHomeAsUpEnabled(true)
        }
    }
}