package com.kakacat.minitool.inquireip

import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView

interface Contract {
    interface Presenter : IPresenter {
        fun requestIpData(input: String?)
    }

    interface View : IView<Presenter?> {
        fun onUpdateDataCallBack(ipBean: IpBean?, result: String?)
    }
}