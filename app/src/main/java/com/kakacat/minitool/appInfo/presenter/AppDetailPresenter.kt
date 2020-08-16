package com.kakacat.minitool.appInfo.presenter

import com.kakacat.minitool.appInfo.activity.AppDetailActivity
import com.kakacat.minitool.appInfo.contract.AppDetailContract
import com.kakacat.minitool.appInfo.model.AppDetailModel
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppDetailPresenter(private val view: AppDetailContract.View) : AppDetailContract.Presenter {

    private val model: AppDetailModel = AppDetailModel()
    private val activity: AppDetailActivity = view.getActivity()

    override fun initData() {
        model.getAppInfoBean(activity)
    }

    override fun saveIcon() {
        GlobalScope.launch {
            val result = model.saveIcon()
            GlobalScope.launch(Dispatchers.Main) {
                view.onSaveIconResult(result)
            }
        }
    }

    override fun openMarket() {
        model.openMarket(activity)
    }

    override fun saveApk() {
        GlobalScope.launch {
            val result = model.saveApk()
            GlobalScope.launch(Dispatchers.Main) {
                view.onSaveApkResult(result)
            }
        }
    }

    override fun openDetailInSetting() {
        model.openDetailInSetting(activity)
    }

    override fun copyMd5() {
        view.onCopyMd5Result(model.copyMd5(activity))
    }

    override val appInfoBean: AppInfoBean?
        get() = model.getAppInfoBean(activity)
}