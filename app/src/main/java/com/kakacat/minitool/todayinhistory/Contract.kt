package com.kakacat.minitool.todayinhistory

import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView
import com.kakacat.minitool.todayinhistory.model.Article

interface Contract {
    interface Presenter : IPresenter {
        fun refreshData()
        val year: Int
        var month: Int
        var day: Int
        val articleList: List<Article?>?
    }

    interface View : IView<Presenter?> {
        fun onUpdateDataCallBack(result: String?, needRefresh: Boolean = false)
        fun showCalendarDialog()
    }
}