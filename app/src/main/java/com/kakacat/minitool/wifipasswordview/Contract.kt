package com.kakacat.minitool.wifipasswordview

import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView
import com.kakacat.minitool.wifipasswordview.model.Wifi

interface Contract {
    interface Presenter : IPresenter {
        fun copyToClipboard(position: Int)
        fun getWifiList() : List<Wifi>
    }

    interface View : IView<Presenter?> {
        fun onCopyCallback(result: String?)
        fun onGetWifiDataCallBack(result: String?)
    }
}