package com.kakacat.minitool.appInfo.contract

import com.kakacat.minitool.appInfo.activity.AppDetailActivity
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean
import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView

interface AppDetailContract {
    interface Presenter : IPresenter {
        fun saveIcon()
        fun openMarket()
        fun saveApk()
        fun openDetailInSetting()
        fun copyMd5()
        val appInfoBean: AppInfoBean?
    }

    interface View : IView<Presenter?> {
        fun saveIcon()
        fun saveApk()
        fun onSaveIconResult(result: String?)
        fun onSaveApkResult(result: String?)
        fun onCopyMd5Result(result: String?)
        fun getActivity() : AppDetailActivity
    }
}