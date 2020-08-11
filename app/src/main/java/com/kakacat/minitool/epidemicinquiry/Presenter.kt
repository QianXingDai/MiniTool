package com.kakacat.minitool.epidemicinquiry

import com.kakacat.minitool.common.util.HttpUtil
import com.kakacat.minitool.common.util.ThreadUtil.callInUiThread
import com.kakacat.minitool.epidemicinquiry.model.GroupBean
import com.kakacat.minitool.epidemicinquiry.model.Model
import okhttp3.Response

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }
    private val callback by lazy {
        object : HttpUtil.Callback {
            var result = "请求错误"
            override fun onSuccess(response: Response?) {
                if (model.handleResponse(response!!)) {
                    callInUiThread(Runnable { view.onUpdateViewSuccessful() })
                } else {
                    result = "处理响应字符串失败"
                }
                callInUiThread(Runnable { view.onUpdateViewError(result) })
            }

            override fun onError() {
                callInUiThread(Runnable { view.onUpdateViewError(result) })
            }
        }
    }

    override fun initData() {
        requestData()
    }

    override fun requestData() {
        model.sendRequest(callback)
    }

    override val groupList: MutableList<GroupBean>?
        get() = model.groupBeanList
}