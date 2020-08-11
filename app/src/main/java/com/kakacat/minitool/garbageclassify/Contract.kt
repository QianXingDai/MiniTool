package com.kakacat.minitool.garbageclassify

import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView
import com.kakacat.minitool.garbageclassify.model.Garbage

interface Contract {
    interface Presenter : IPresenter {
        fun requestData(s: String?)
        val garbageList: List<Garbage>?
    }

    interface View : IView<Presenter?> {
        fun requestData(s: String?)
        fun onRequestCallBack(result: String?)
        fun showContentView(position: Int)
    }
}