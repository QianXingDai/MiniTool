package com.kakacat.minitool.todayinhistory

import com.kakacat.minitool.todayinhistory.model.Article
import com.kakacat.minitool.todayinhistory.model.Model
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Presenter(private val view: Contract.View) : Contract.Presenter {
    private val model by lazy { Model() }

    override fun initData() {
        model.initData()
    }

    override fun refreshData() {
        GlobalScope.launch(Dispatchers.Default) {
            val response = model.sendRequest()
            val handleResult = model.handleHistoryResponse(response)
            var result = "请求错误"
            if(handleResult){
                result = "处理成功"
                view.onUpdateDataCallBack(result, true)
            }else{
                view.onUpdateDataCallBack(result)
            }
        }
    }

    override val articleList: List<Article>
        get() = model.getArticleList()

    override val year: Int
        get() = model.year

    override var month: Int
        get() = model.month
        set(month) {
            model.month = month
        }

    override var day: Int
        get() = model.day
        set(day) {
            model.day = day
        }
}