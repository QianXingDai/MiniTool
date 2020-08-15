package com.kakacat.minitool.phoneartribution

import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView
import com.kakacat.minitool.phoneartribution.model.PhoneNumber

interface Contract {
    interface Presenter : IPresenter {
        fun requestData(phoneNumber: String?)
    }

    interface View : IView<Presenter?> {
        fun onRequestDataCallBack(phoneNumber: PhoneNumber?, result: String?)
    }
}