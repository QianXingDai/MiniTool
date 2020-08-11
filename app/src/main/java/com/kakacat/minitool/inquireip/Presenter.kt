package com.kakacat.minitool.inquireip

import com.kakacat.minitool.common.util.HttpUtil
import com.kakacat.minitool.common.util.HttpUtil.sendOkHttpRequest
import com.kakacat.minitool.common.util.ThreadUtil.callInUiThread
import okhttp3.Response

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }
    private val callback by lazy {
        object : HttpUtil.Callback {
            var result = "请求错误"
            override fun onSuccess(response: Response?) {
                val ipBean = model.handleIpDataResponse(response!!)
                result = if (ipBean == null) {
                    "处理响应失败"
                } else {
                    "处理成功"
                }
                callInUiThread(Runnable { view.onUpdateDataCallBack(ipBean, result) })
            }

            override fun onError() {
                callInUiThread(Runnable { view.onUpdateDataCallBack(null, result) })
            }
        }
    }

    override fun initData() {}

    override fun requestIpData(input: String?) {
        if (!model.validateIp(input)) {
            view.onUpdateDataCallBack(null, "输入格式错误")
        } else {
            model.sendRequest(input!!,callback)
        }
    }

}