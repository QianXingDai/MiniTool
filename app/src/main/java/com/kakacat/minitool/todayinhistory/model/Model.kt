package com.kakacat.minitool.todayinhistory.model

import com.google.gson.Gson
import com.kakacat.minitool.common.util.HttpUtil
import okhttp3.Response
import org.json.JSONObject
import java.util.*

class Model {

    private var articleList: MutableList<Article>? = null
    var year = 0
        private set
    var month = 0
    var day = 0

    fun initData() {
        articleList = getArticleList()
        val c = Calendar.getInstance()
        year = c[Calendar.YEAR]
        month = c[Calendar.MONTH] + 1
        day = c[Calendar.DATE]
    }

    fun sendRequest(): Response? {
        return HttpUtil.sendRequest(address)
    }

    fun handleHistoryResponse(response: Response?): Boolean {
        try {
            if(response?.body == null){
                return false
            }
            val s = response.body!!.string()
            val jsonObject = JSONObject(s)
            val result = jsonObject.getJSONArray("result")
            val gson = Gson()
            articleList!!.clear()
            for (i in 0 until result.length()) {
                val str = result.getJSONObject(i).toString()
                val article = gson.fromJson(str, Article::class.java)
                articleList!!.add(article)
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun getArticleList(): MutableList<Article> {
        if (articleList == null) {
            articleList = ArrayList()
        }
        return articleList!!
    }

    private val address: String
        get() = "$HOST$KEY&v=1.0&month=$month&day=$day"

    companion object {
        private const val HOST = "http://api.juheapi.com/japi/toh?key="
        private const val KEY = "9aac7a73878303c4559180d1272e4a8e"
    }
}