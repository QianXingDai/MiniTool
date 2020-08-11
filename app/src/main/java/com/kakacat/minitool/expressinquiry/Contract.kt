package com.kakacat.minitool.expressinquiry

import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView
import com.kakacat.minitool.expressinquiry.model.Delivery

interface Contract {
    interface View : IView<Presenter> {
        fun onRequestCallback(result: String?, needRefresh: Boolean = false)
        fun showWindows(fab: android.view.View?)
    }

    interface Presenter : IPresenter {
        fun saveData()
        fun requestData(code: String?)
        fun refreshAll()
        val dataFromClipBoard: CharSequence?
        fun getDeliveryList(index: Int): List<Delivery>?
    }
}