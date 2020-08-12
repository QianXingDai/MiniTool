package com.kakacat.minitool.wifipasswordview

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakacat.minitool.R
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.ui.RecycleViewListener.OnItemLongClick
import com.kakacat.minitool.common.util.UiUtil.initToolbar
import com.kakacat.minitool.common.util.UiUtil.setTranslucentStatusBarWhite
import com.kakacat.minitool.common.util.UiUtil.showToast

class WifiPwdActivity : BaseActivity(), Contract.View {

    private var presenter: Contract.Presenter? = null
    private val adapter by lazy { Adapter(presenter!!.getWifiList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifi_pwd_view)
        initView()
        initData()
    }

    override fun onDestroy() {
        presenter = null
        super.onDestroy()
    }

    override fun initData() {
        presenter!!.initData()
    }

    override fun initView() {
        setTranslucentStatusBarWhite(this)
        initToolbar(this, true)
        presenter = Presenter(this)

        val recyclerView = findViewById<RecyclerView>(R.id.rv_wifi)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = GridLayoutManager(this, 1)
        adapter.setLongClickListener(object : OnItemLongClick {
            override fun onLongClick(v: View?, position: Int) {
                presenter!!.copyToClipboard(position)
            }
        })
    }

    override fun onCopyCallback(result: String?) {
        showToast(this, result)
    }

    override fun onGetWifiDataCallBack(result: String?) {
        showToast(this, result)
        adapter.notifyDataSetChanged()
    }
}