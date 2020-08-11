package com.kakacat.minitool.appInfo.presenter

import bolts.Continuation
import bolts.Task
import com.kakacat.minitool.appInfo.activity.AppDetailActivity
import com.kakacat.minitool.appInfo.contract.AppDetailContract
import com.kakacat.minitool.appInfo.model.AppDetailModel
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean

class AppDetailPresenter(private val view: AppDetailContract.View) : AppDetailContract.Presenter {

    private val model: AppDetailModel = AppDetailModel()
    private val activity: AppDetailActivity = view.getActivity()

    override fun initData() {
        model.getAppInfoBean(activity)
    }

    override fun saveIcon() {
        Task.callInBackground { model.saveIcon() }.onSuccess(Continuation<String, Void?> { task: Task<String> ->
            view.onSaveIconResult(task.result)
            null
        }, Task.UI_THREAD_EXECUTOR)
    }

    override fun openMarket() {
        model.openMarket(activity)
    }

    override fun saveApk() {
        Task.callInBackground { model.saveApk() }.onSuccess(Continuation<String, Void?> { task: Task<String> ->
            view.onSaveApkResult(task.result)
            null
        }, Task.UI_THREAD_EXECUTOR)
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