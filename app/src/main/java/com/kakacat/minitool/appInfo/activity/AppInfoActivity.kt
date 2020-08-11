package com.kakacat.minitool.appInfo.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.widget.NestedScrollView
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

class AppInfoActivity : BaseActivity(), AppInfoContract.View, View.OnClickListener {

    private var presenter: AppInfoContract.Presenter? = AppInfoPresenter(this)
    private var nestedScrollView: NestedScrollView? = null
    private var progressBar: ProgressBar? = null
    private var linearLayout: LinearLayout? = null
    private var sortDialog: MyPopupWindow? = null
    private var apiPercentAdapter: ApiPercentAdapter? = null
    private var appInfoAdapter: AppInfoAdapter? = null

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
        nestedScrollView = findViewById(R.id.nested_scroll_view)
        linearLayout = findViewById(R.id.linear_layout)
        progressBar = findViewById(R.id.progress_bar)
        initApiPercentView()
        initAppInfoView()
    }

    override fun onUpdateDataSet() {
        apiPercentAdapter!!.notifyDataSetChanged()
        appInfoAdapter!!.notifyDataSetChanged()
        UiUtil.dismissLoadingWindow()
        linearLayout!!.visibility = View.VISIBLE
        progressBar!!.visibility = View.INVISIBLE
    }

    @SuppressLint("SetTextI18n")
    private fun initToolbar() {
        UiUtil.initToolbar(this, false)
        val tvAndroidVersion = findViewById<TextView>(R.id.tv_android_version)
        val tvSecurityPatchLevel = findViewById<TextView>(R.id.tv_security_patch_level)
        val tvDeviceName = findViewById<TextView>(R.id.tv_device_name)
        tvAndroidVersion.text = "Android " + Build.VERSION.RELEASE
        tvSecurityPatchLevel.text = Build.VERSION.SECURITY_PATCH
        tvDeviceName.text = Build.DEVICE
    }

    private fun initApiPercentView() {
        val rvApiPercent = findViewById<RecyclerView>(R.id.rv_api_percent)
        apiPercentAdapter = ApiPercentAdapter(presenter!!.apiPercentBeanList!!)
        rvApiPercent.adapter = apiPercentAdapter
        rvApiPercent.layoutManager = LinearLayoutManager(this)
    }

    private fun initAppInfoView() {
        val rvAppInfo = findViewById<RecyclerView>(R.id.rv_app_info)
        appInfoAdapter = AppInfoAdapter(presenter!!.appInfoBeanList!!)
        rvAppInfo.adapter = appInfoAdapter
        rvAppInfo.layoutManager = LinearLayoutManager(this)
        appInfoAdapter!!.setOnClickListener(object : RecycleViewListener.OnItemClick {
            override fun onClick(v: View?, position: Int) {
                val intent = Intent(this@AppInfoActivity, AppDetailActivity::class.java)
                intent.putExtra("appInfo", presenter!!.appInfoBeanList!![position - 1])
                startActivity(intent)
            }
        })
    }


    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab -> UiUtil.slideUpToTop(nestedScrollView!!)
            R.id.iv_sort -> showSortDialog()
            R.id.rb_sort_by_app_name -> sort(AppInfoModel.SORT_BY_APP_NAME)
            R.id.rb_sort_by_target_api -> sort(AppInfoModel.SORT_BY_TARGET_API)
            R.id.rb_sort_by_min_api -> sort(AppInfoModel.SORT_BY_MIN_API)
            R.id.rb_sort_by_first_install_time -> sort(AppInfoModel.SORT_BY_FIRST_INSTALL_TIME)
            R.id.rb_sort_by_last_update_time -> sort(AppInfoModel.SORT_BY_LAST_UPDATE_TIME)
        }
    }

    override fun sort(sortFlag: Int) {
        sortDialog!!.dismiss()
        UiUtil.showLoading(this, nestedScrollView)
        presenter!!.sortAppInfoList(sortFlag)
    }

    override fun showSortDialog() {
        if (sortDialog == null) {
            val contentView = LayoutInflater.from(this).inflate(R.layout.sort_dialog_layout, nestedScrollView, false)
            sortDialog = MyPopupWindow(this, contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        sortDialog!!.showAtLocation(nestedScrollView!!, Gravity.CENTER, 0, 0)
    }
}