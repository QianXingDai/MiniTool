package com.kakacat.minitool.appInfo.contract

import com.kakacat.minitool.appInfo.model.bean.ApiPercentBean
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean
import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView

interface AppInfoContract {
    interface Presenter : IPresenter {
        fun sortAppInfoList(sortFlag: Int)
        val apiPercentBeanList: List<ApiPercentBean?>?
        val appInfoBeanList: List<AppInfoBean>?
    }

    interface View : IView<Presenter?> {
        fun onUpdateDataSet()
        fun sort(sortFlag: Int)
        fun showSortDialog()
    }
}