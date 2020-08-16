package com.kakacat.minitool.phoneartribution

import com.kakacat.minitool.phoneartribution.model.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }

    override fun requestData(input: String?) {
        GlobalScope.launch(Dispatchers.Default) {
            if (model.validateInput(input)) {
                view.onRequestDataCallBack(null, "输入错误")
            } else {
                val response = model.sendRequest(input!!)
                val handleResult = model.handleResponse(response)
                val result: String
                if(handleResult != null){
                    result = "请求成功"
                    handleResult.number = input
                }else{
                    result = "处理响应数据失败"
                }
                view.onRequestDataCallBack(handleResult,result)
            }
        }
    }

    override fun initData() {}
}