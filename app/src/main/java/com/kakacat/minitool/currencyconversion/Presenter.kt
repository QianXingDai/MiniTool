package com.kakacat.minitool.currencyconversion

import com.kakacat.minitool.common.util.HttpUtil
import com.kakacat.minitool.common.util.ThreadUtil.callInUiThread
import com.kakacat.minitool.currencyconversion.model.CountryBean
import com.kakacat.minitool.currencyconversion.model.Model
import okhttp3.Response

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }
    private val context by lazy { view.context }
    private val callback by lazy {
        object : HttpUtil.Callback {
            var result = "请求错误"
            override fun onSuccess(response: Response?) {
                result = if (!model.handleResponse(response!!)) {
                    "处理相应字符串错误"
                } else {
                    "处理成功"
                }
                callInUiThread(Runnable { view.onRefreshExchangeRate(result) })
            }

            override fun onError() {
                callInUiThread(Runnable { view.onRefreshExchangeRate(result) })
            }
        }
    }

    override fun initData() {
        model.readRateFromLocal(context)
    }

    override fun refreshExchangeRate() {
        model.sendRequest(callback)
    }

    override fun getResult(`val`: CharSequence, rate1: Double, rate2: Double): String {
        return model.getResult(`val`, rate1, rate2)
    }

    override val countryList: List<CountryBean>
        get() = model.getCountryList()
}