package com.kakacat.minitool.appInfo.presenter

import com.kakacat.minitool.appInfo.contract.AppInfoContract
import com.kakacat.minitool.appInfo.model.AppInfoModel
import com.kakacat.minitool.appInfo.model.bean.ApiPercentBean
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean
import com.kakacat.minitool.common.util.ThreadUtil.callInBackground
import com.kakacat.minitool.common.util.ThreadUtil.callInUiThread

class AppInfoPresenter(private val view: AppInfoContract.View) : AppInfoContract.Presenter {

    private val appInfoModel: AppInfoModel = AppInfoModel()
    
    override fun initData() {
        callInBackground(Runnable {
            appInfoModel.initData(view.context.packageManager)
            callInUiThread(Runnable { view.onUpdateDataSet() })
        })
    }

    override fun sortAppInfoList(sortFlag: Int) {
        callInBackground(Runnable {
            appInfoModel.sortAppInfoList(sortFlag)
            callInUiThread(Runnable { view.onUpdateDataSet() })
        })
    }

    override val apiPercentBeanList: List<ApiPercentBean>
        get() = appInfoModel.getApiPercentBeanList()

    override val appInfoBeanList: List<AppInfoBean>
        get() = appInfoModel.getAppInfoBeanList()
}