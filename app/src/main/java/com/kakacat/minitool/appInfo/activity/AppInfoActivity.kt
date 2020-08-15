package com.kakacat.minitool.appInfo.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kakacat.minitool.R
import com.kakacat.minitool.appInfo.adapter.ApiPercentAdapter
import com.kakacat.minitool.appInfo.adapter.AppInfoAdapter
import com.kakacat.minitool.appInfo.contract.AppInfoContract
import com.kakacat.minitool.appInfo.model.AppInfoModel
import com.kakacat.minitool.appInfo.presenter.AppInfoPresenter
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.ui.RecycleViewListener
import com.kakacat.minitool.common.ui.view.MyPopupWindow
import com.kakacat.minitool.common.util.UiUtil
import kotlinx.android.synthetic.main.activity_app_info.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppInfoActivity : BaseActivity(), AppInfoContract.View, View.OnClickListener {

    private var presenter: AppInfoContract.Presenter? = AppInfoPresenter(this)
    private val sortDialog by lazy {
        val contentView = LayoutInflater.from(this).inflate(R.layout.sort_dialog_layout, nested_scroll_view, false)
        MyPopupWindow(this, contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    private val apiPercentAdapter by lazy { ApiPercentAdapter(presenter!!.apiPercentBeanList!!) }
    private val appInfoAdapter by lazy { AppInfoAdapter(presenter!!.appInfoBeanList!!) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)
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
        UiUtil.setTranslucentStatusBarWhite(this)
        initToolbar()

        rv_api_percent.adapter = apiPercentAdapter
        rv_api_percent.layoutManager = LinearLayoutManager(this)

        val rvAppInfo = findViewById<RecyclerView>(R.id.rv_app_info)
        rvAppInfo.adapter = appInfoAdapter
        rvAppInfo.layoutManager = LinearLayoutManager(this)
        appInfoAdapter.setOnClickListener(object : RecycleViewListener.OnItemClick {
            override fun onClick(v: View?, position: Int) {
                val intent = Intent(this@AppInfoActivity, AppDetailActivity::class.java)
                intent.putExtra("appInfo", presenter!!.appInfoBeanList!![position - 1])
                startActivity(intent)
            }
        })
    }

    override fun onUpdateDataSet() {
        GlobalScope.launch(Dispatchers.Main) {
            apiPercentAdapter.notifyDataSetChanged()
            appInfoAdapter.notifyDataSetChanged()
            UiUtil.dismissLoadingWindow()
            linear_layout.visibility = View.VISIBLE
            progress_bar.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initToolbar() {
        UiUtil.initToolbar(this, false)
        tv_android_version.text = "Android " + Build.VERSION.RELEASE
        tv_security_patch_level.text = Build.VERSION.SECURITY_PATCH
        tv_device_name.text = Build.DEVICE
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab -> UiUtil.slideUpToTop(nested_scroll_view)
            R.id.iv_sort -> showSortDialog()
            R.id.rb_sort_by_app_name -> sort(AppInfoModel.SORT_BY_APP_NAME)
            R.id.rb_sort_by_target_api -> sort(AppInfoModel.SORT_BY_TARGET_API)
            R.id.rb_sort_by_min_api -> sort(AppInfoModel.SORT_BY_MIN_API)
            R.id.rb_sort_by_first_install_time -> sort(AppInfoModel.SORT_BY_FIRST_INSTALL_TIME)
            R.id.rb_sort_by_last_update_time -> sort(AppInfoModel.SORT_BY_LAST_UPDATE_TIME)
        }
    }

    override fun sort(sortFlag: Int) {
        sortDialog.dismiss()
        UiUtil.showLoading(this, nested_scroll_view)
        presenter!!.sortAppInfoList(sortFlag)
    }

    override fun showSortDialog() {
        sortDialog.showAtLocation(nested_scroll_view, Gravity.CENTER, 0, 0)
    }
}