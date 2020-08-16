package com.kakacat.minitool.expressinquiry

import com.kakacat.minitool.common.util.SystemUtil.getDataFormClipBoard
import com.kakacat.minitool.expressinquiry.model.Delivery
import com.kakacat.minitool.expressinquiry.model.Model
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
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
        GlobalScope.launch {
            if (!model.validateInput(code!!)) {
                view.onRequestCallback("输入错误,请检查输入")
            } else {
                if (model.isInSignedList(code)) {
                    view.onRequestCallback("已经在签收列表哟")
                } else {
                    val response = model.sendRequest(code)
                    val handleResult = model.handleResponse(response!!,code)
                    if(handleResult){
                        view.onRequestCallback("查询成功", true)
                    }else{
                        view.onRequestCallback("错误")
                    }
                }
            }
        }
    }

    override fun refreshAll() {
        val list = getDeliveryList(0)
        if (list!!.isEmpty()) {
            view.onRequestCallback("全部签收了哟")
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