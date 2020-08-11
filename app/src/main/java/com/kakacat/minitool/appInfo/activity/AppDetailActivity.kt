package com.kakacat.minitool.appInfo.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.kakacat.minitool.R
import com.kakacat.minitool.appInfo.contract.AppDetailContract
import com.kakacat.minitool.appInfo.presenter.AppDetailPresenter
import com.kakacat.minitool.common.base.BaseActivity
import com.kakacat.minitool.common.util.UiUtil
import kotlinx.android.synthetic.main.activity_app_detail.*
import kotlinx.android.synthetic.main.activity_app_detail.iv_app_icon
import kotlinx.android.synthetic.main.item_app_info.*
import kotlinx.android.synthetic.main.item_app_info.tv_app_name

class AppDetailActivity : BaseActivity(), AppDetailContract.View, View.OnClickListener {

    private var presenter: AppDetailContract.Presenter? = AppDetailPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_detail)
        initData()
        initView()
    }

    override fun onDestroy() {
        presenter = null
        super.onDestroy()
    }

    override fun initData() {
        presenter!!.initData()
    }

    @SuppressLint("SetTextI18n")
    override fun initView() {
        UiUtil.initToolbar(this, false)
        val appInfoBean = presenter!!.appInfoBean!!
        tv_app_name.text = appInfoBean.appName
        tv_package_name.text = appInfoBean.packageName
        tv_version_name.text = appInfoBean.versionName
        tv_first_install_time.text = appInfoBean.firstInstallTime2
        tv_last_update_time.text = appInfoBean.lastUpdateTime2
        tv_target_api.text = appInfoBean.targetSdkVersion.toString()
        tv_min_api.text = appInfoBean.minSdkVersion.toString()
        tv_md5_sign.text = appInfoBean.signMd5
        tv_permission.text = appInfoBean.permission
        tv_header3.text = "权限声明" + "(" + appInfoBean.permissionCount + "个)"
        iv_app_icon.setImageBitmap(appInfoBean.bitmap)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.bt_save_icon -> saveIcon()
            R.id.bt_open_market -> presenter!!.openMarket()
            R.id.bt_get_apk -> saveApk()
            R.id.bt_open_detail -> presenter!!.openDetailInSetting()
            R.id.bt_copy_md5 -> presenter!!.copyMd5()
        }
    }

    override fun saveIcon() {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, SAVE_ICON)
        } else {
            presenter!!.saveIcon()
        }
    }

    override fun onSaveIconResult(result: String?) {
        UiUtil.showToast(this, result)
    }

    override fun saveApk() {
        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, SAVE_APK)
        } else {
            presenter!!.saveApk()
        }
    }

    override fun onCopyMd5Result(result: String?) {
        UiUtil.showToast(this, result)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == SAVE_ICON) {
                presenter!!.saveIcon()
            } else if (requestCode == SAVE_APK) {
                presenter!!.saveApk()
            }
        } else {
            UiUtil.showToast(this, "获取存储权限失败")
        }
    }

    override fun onSaveApkResult(result: String?) {
        UiUtil.showToast(this, result)
    }

     override fun getActivity(): AppDetailActivity {
        return this
    }

    companion object {
        private const val SAVE_ICON = 1
        private const val SAVE_APK = 2
    }
}