package com.kakacat.minitool.epidemicinquiry

import com.kakacat.minitool.epidemicinquiry.model.GroupBean
import com.kakacat.minitool.epidemicinquiry.model.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }

    override fun initData() {
        requestData()
    }

    override fun requestData() {
        GlobalScope.launch(Dispatchers.Default){
            val response = model.sendRequest()
            val handleResult = model.handleResponse(response!!)
            if(handleResult){
                view.onUpdateViewSuccessful()
            }else{
                view.onUpdateViewError("错误")
            }
        }
    }

    override val groupList: MutableList<GroupBean>?
        get() = model.groupBeanList
}