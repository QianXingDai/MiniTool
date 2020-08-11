package com.kakacat.minitool.currencyconversion

import com.kakacat.minitool.common.base.IPresenter
import com.kakacat.minitool.common.base.IView
import com.kakacat.minitool.currencyconversion.model.CountryBean

interface Contract {
    interface Presenter : IPresenter {
        fun refreshExchangeRate()
        fun getResult(`val`: CharSequence, rate1: Double, rate2: Double): String
        val countryList: List<CountryBean>
    }

    interface View : IView<Presenter?> {
        fun onRefreshExchangeRate(result: String?)
        fun onTextChanged(s: CharSequence, flag: Int)
    }
}