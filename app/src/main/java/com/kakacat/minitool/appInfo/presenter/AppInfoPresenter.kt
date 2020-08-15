package com.kakacat.minitool.appInfo.presenter

import com.kakacat.minitool.appInfo.contract.AppInfoContract
import com.kakacat.minitool.appInfo.model.AppInfoModel
import com.kakacat.minitool.appInfo.model.bean.ApiPercentBean
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppInfoPresenter(private val view: AppInfoContract.View) : AppInfoContract.Presenter {

    private val appInfoModel by lazy { AppInfoModel() }
    
    override fun initData() {
        GlobalScope.launch(Dispatchers.Default) {
            appInfoModel.initData(view.context.packageManager)
            view.onUpdateDataSet()
        }
    }

    override fun sortAppInfoList(sortFlag: Int) {
        GlobalScope.launch(Dispatchers.Default) {
            appInfoModel.sortAppInfoList(sortFlag)
            view.onUpdateDataSet()
        }
    }

    override val apiPercentBeanList: List<ApiPercentBean>
        get() = appInfoModel.getApiPercentBeanList()

    override val appInfoBeanList: List<AppInfoBean>
        get() = appInfoModel.getAppInfoBeanList()
}