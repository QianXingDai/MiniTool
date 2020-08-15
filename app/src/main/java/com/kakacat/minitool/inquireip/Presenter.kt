package com.kakacat.minitool.inquireip

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }

    override fun initData() {}

    override fun requestIpData(input: String?) {
        GlobalScope.launch(Dispatchers.Default) {
            if (!model.validateIp(input)) {
                view.onUpdateDataCallBack(null, "输入格式错误")
            } else {
                val response = model.sendRequest(input!!)
                val handlerResult = model.handleIpDataResponse(response!!)
                val result = if (handlerResult == null) {
                        "处理响应失败"
                } else {
                        "处理成功"
                }
                view.onUpdateDataCallBack(handlerResult, result)
            }
        }
    }
}