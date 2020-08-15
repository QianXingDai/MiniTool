package com.kakacat.minitool.epidemicinquiry.model

import com.google.gson.Gson
import com.kakacat.minitool.common.util.HttpUtil
import okhttp3.Response
import org.json.JSONObject
import java.util.*

class Model {

    var groupBeanList: MutableList<GroupBean>? = null
        get() {
            if (field == null) {
                field = ArrayList()
            }
            return field
        }
        private set

    fun sendRequest(): Response?{
        return HttpUtil.sendRequest(ADDRESS)
    }

    fun handleResponse(response: Response): Boolean {
        val s = response.body!!.string()
        val jsonObject = JSONObject(s)
        val jsonArray = jsonObject.getJSONArray("newslist")
        val gson = Gson()
        for (i in 0 until jsonArray.length()) {
            val jsonObject1 = jsonArray.getJSONObject(i)
            val groupBean = GroupBean()
            groupBean.location = jsonObject1.getString("provinceShortName")
            groupBean.currentConfirmCount = jsonObject1.getString("currentConfirmedCount")
            groupBean.totalConfirmCount = jsonObject1.getString("confirmedCount")
            groupBean.susPectCount = jsonObject1.getString("suspectedCount")
            groupBean.curedCount = jsonObject1.getString("curedCount")
            groupBean.deadCount = jsonObject1.getString("deadCount")
            val childBeanList = groupBean.childBeanList
            val jsonArray1 = jsonObject1.getJSONArray("cities")
            for (i1 in 0 until jsonArray1.length()) {
                val childBean = gson.fromJson(jsonArray1.getJSONObject(i1).toString(), ChildBean::class.java)
                childBeanList!!.add(childBean)
            }
            groupBeanList!!.add(groupBean)
        }
        return true
    }

    companion object {
        //疫情省市情况查询接口
        private const val HOST = "http://api.tianapi.com/txapi/ncovcity/index"
        //疫情省市情况查询接口key
        private const val KEY = "2675f5858d0ba338b6d8d4f93cfe17be"
        //疫情省市情况查询地址
        private const val ADDRESS = "$HOST?key=$KEY"
    }
}