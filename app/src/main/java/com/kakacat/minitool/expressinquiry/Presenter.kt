package com.kakacat.minitool.expressinquiry

import com.kakacat.minitool.common.util.HttpUtil
import com.kakacat.minitool.common.util.SystemUtil.getDataFormClipBoard
import com.kakacat.minitool.common.util.ThreadUtil.callInUiThread
import com.kakacat.minitool.expressinquiry.model.Delivery
import com.kakacat.minitool.expressinquiry.model.Model
import okhttp3.Response
import java.util.function.Consumer

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }
    private val context by lazy { view.context }

    override fun initData() {
        model.initData(context)
    }

    override fun saveData() {
        model.saveDataToLocal(context)
    }

    override fun requestData(code: String?) {
        if (!model.validateInput(code!!)) {
            view.onRequestCallback("输入错误,请检查输入")
        } else {
            if (model.isInSignedList(code)) {
                view.onRequestCallback("已经在签收列表哟")
            } else {
                model.sendRequest(code, object : HttpUtil.Callback {
                    override fun onSuccess(response: Response?) {
                        if (model.handleResponse(response!!, code)) {
                            callInUiThread(Runnable { view.onRequestCallback("查询成功", true) })
                        } else {
                            callInUiThread(Runnable { view.onRequestCallback("处理错误") })
                        }
                    }

                    override fun onError() {
                        callInUiThread(Runnable { view.onRequestCallback("请求错误") })
                    }
                })
            }
        }
    }

    override fun refreshAll() {
        val list = getDeliveryList(0)
        if (list!!.isEmpty()) {
            callInUiThread(Runnable { view.onRequestCallback("全部签收了哟") })
        } else {
            list.forEach(Consumer { delivery: Delivery? -> requestData(delivery!!.code) })
        }
    }

    override val dataFromClipBoard: CharSequence
        get() = getDataFormClipBoard(context)

    override fun getDeliveryList(index: Int): List<Delivery>? {
        return model.getList(index)
    }
}