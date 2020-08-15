package com.kakacat.minitool.phoneartribution.model

import android.text.TextUtils
import com.kakacat.minitool.common.util.HttpUtil
import okhttp3.Response
import org.json.JSONObject

class Model {

    fun validateInput(number: String?): Boolean{
        if(!TextUtils.isEmpty(number)){
            return number!!.trim().length != 11
        }
        return false;
    }

    fun sendRequest(number: String): Response?{
        return HttpUtil.sendRequest(getAddress(number))
    }

    fun handleResponse(response: Response?): PhoneNumber? {
        try {
            if(response == null){
                return null
            }
            val s = response.body!!.string()
            if (!TextUtils.isEmpty(s)) {
                val jsonObject = JSONObject(s)
                val result = jsonObject.getJSONObject("result")
                return PhoneNumber(null, result.getString("province"), result.getString("city"), result.getString("areacode"), result.getString("zip"), result.getString("company"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getAddress(number: String): String {
        return "$HOST$number&key=$KEY"
    }

    companion object {
        private const val HOST = "http://apis.juhe.cn/mobile/get?phone="
        private const val KEY = "a61898e25da1484f93ccf01e2ebe6ff7"
    }
}