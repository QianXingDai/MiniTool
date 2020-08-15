package com.kakacat.minitool.garbageclassify

import com.kakacat.minitool.garbageclassify.model.Garbage
import com.kakacat.minitool.garbageclassify.model.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }

    override fun initData() {}

    override fun requestData(s: String?) {
        GlobalScope.launch(Dispatchers.Default) {
            if (!model.validate(s)) {
                view.onRequestCallBack("请输入内容")
            } else {
                val response = model.sendRequest(s!!)
                val handleResult = model.handleResponse(response!!)
                var result = "请求错误"
                if(handleResult){
                    result = if (model.handleResponse(response)) {
                        "处理成功"
                    } else {
                        "处理响应字符串失败"
                    }
                    view.onRequestCallBack(result)
                }else{
                    view.onRequestCallBack(result)
                }
            }
        }
    }

    override val garbageList: List<Garbage>?
        get() = model.garbageList
}