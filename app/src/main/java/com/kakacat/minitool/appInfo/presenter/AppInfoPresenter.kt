package com.kakacat.minitool.appInfo.presenter

import com.kakacat.minitool.appInfo.contract.AppInfoContract
import com.kakacat.minitool.appInfo.model.AppInfoModel
import com.kakacat.minitool.appInfo.model.bean.ApiPercentBean
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean
import com.kakacat.minitool.common.util.SystemUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class AppInfoPresenter(private val view: AppInfoContract.View) : AppInfoContract.Presenter {

    private val appInfoModel by lazy { AppInfoModel() }
    
    override fun initData() {
        GlobalScope.launch {
            val job = GlobalScope.async { appInfoModel.initData(view.context.packageManager) }
            job.await()
            view.onUpdateDataSet()
        }
    }

    override fun sortAppInfoList(sortFlag: Int) {
        GlobalScope.launch {
            appInfoModel.sortAppInfoList(sortFlag)
            view.onUpdateDataSet()
        }
    }

    override val apiPercentBeanList: List<ApiPercentBean>
        get() = appInfoModel.getApiPercentBeanList()

    override val appInfoBeanList: List<AppInfoBean>
        get() = appInfoModel.getAppInfoBeanList()
}