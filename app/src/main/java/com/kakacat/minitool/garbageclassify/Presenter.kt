package com.kakacat.minitool.garbageclassify

import com.kakacat.minitool.common.util.HttpUtil
import com.kakacat.minitool.common.util.ThreadUtil.callInUiThread
import com.kakacat.minitool.garbageclassify.model.Garbage
import com.kakacat.minitool.garbageclassify.model.Model
import okhttp3.Response

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }
    private val callback by lazy{
        object :HttpUtil.Callback {
            var result = "输入错误或者找不到结果"
            override fun onSuccess(response: Response?) {
                result = if (model.handleResponse(response!!)) {
                    "处理成功"
                } else {
                    "处理响应字符串失败"
                }
                callInUiThread(Runnable { view.onRequestCallBack(result) })
            }

            override fun onError() {
                callInUiThread(Runnable { view.onRequestCallBack(result) })
            }
        }
    }

    override fun initData() {}

    override fun requestData(s: String?) {
        if (!model.validate(s)) {
            view.onRequestCallBack("请输入内容")
        } else {
            model.sendRequest(s!!,callback)
        }
    }

    override val garbageList: List<Garbage>?
        get() = model.garbageList
}