package com.kakacat.minitool.currencyconversion

import com.kakacat.minitool.currencyconversion.model.CountryBean
import com.kakacat.minitool.currencyconversion.model.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }
    private val context by lazy { view.context }

    override fun initData() {
        model.readRateFromLocal(context)
    }

    override fun refreshExchangeRate() {
        GlobalScope.launch(Dispatchers.Default){
            val response = model.sendRequest()
            val handleResult = model.handleResponse(response)
            if(handleResult){
                view.onRefreshExchangeRateAsync("处理成功")
            }else{
                view.onRefreshExchangeRateAsync("处理失败")
            }
        }
    }

    override fun getResult(`val`: CharSequence, rate1: Double, rate2: Double): String {
        return model.getResult(`val`, rate1, rate2)
    }

    override val countryList: List<CountryBean>
        get() = model.getCountryList()
}