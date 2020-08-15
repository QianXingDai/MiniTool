package com.kakacat.minitool.translation

import android.text.TextUtils
import com.kakacat.minitool.translation.model.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }
    private val context by lazy { view.context }

    override fun initData() {
        model.initData()
    }

    override val languageList1: List<String>
        get() = model.languageList1

    override val languageList2: List<String>
        get() = model.languageList2

    override val collectionList: MutableList<String>
        get() = model.collectionList

    override fun addToMyFavourite(source: String?, target: String?) {
        if (TextUtils.isEmpty(source)) {
            view.onAddToMyFavouriteCallBack("请输入内容")
        } else {
            model.addToMyFavourite(source!!, target!!, context)
            view.onAddToMyFavouriteCallBack("收藏成功")
        }
    }

    override fun requestData(input: String?, from: CharSequence, to: CharSequence) {
        GlobalScope.launch(Dispatchers.Main) {
            if(!model.validateInput(input)){
                view.onRequestCallBack(null, "输入错误")
            } else {
                val response = model.sendRequest(input!!,from,to)
                val handleResult = model.handleTranslationResponse(response)
                var result = "请求错误"
                if(handleResult != null){
                    result = if (TextUtils.isEmpty(handleResult)) {
                        "处理错误"
                    } else {
                        "请求成功"
                    }
                    view.onRequestCallBack(handleResult, result)
                }else{
                    view.onRequestCallBack(null, result)
                }
            }
        }
    }
}