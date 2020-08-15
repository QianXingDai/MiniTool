package com.kakacat.minitool.garbageclassify.model

import android.text.TextUtils
import com.google.gson.Gson
import com.kakacat.minitool.common.util.HttpUtil
import okhttp3.Response
import org.json.JSONObject

class Model {

    var garbageList : MutableList<Garbage>? = null
        get() {
            if(field == null){
                field = ArrayList()
            }
            return field
        }
        private set

    fun handleResponse(response: Response): Boolean {
        try {
            val s = response.body!!.string()
            if (TextUtils.isEmpty(s)) {
                return false
            }
            val jsonObject = JSONObject(s)
            val garbageObjects = jsonObject.getJSONArray("newslist")
            val gson = Gson()
            garbageList!!.clear()
            for (i in 0 until garbageObjects.length()) {
                val str = garbageObjects.getJSONObject(i).toString()
                garbageList!!.add(gson.fromJson(str, Garbage::class.java))
            }
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun sendRequest(input: String): Response?{
        return HttpUtil.sendRequest(getAddress(input))
    }

    fun validate(input: String?) : Boolean{
        return !TextUtils.isEmpty(input)
    }

    fun getAddress(input: String): String {
        return "$HOST$KEY&word=$input"
    }

    companion object {
        private const val HOST = "https://api.tianapi.com/txapi/lajifenlei/index?key="
        private const val KEY = "c3886d3637d56c2730a4a7066fb9fa47"
    }
}