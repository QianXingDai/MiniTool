package com.kakacat.minitool.inquireip

import android.text.TextUtils
import com.kakacat.minitool.common.util.HttpUtil
import com.kakacat.minitool.common.util.SystemUtil
import okhttp3.Response
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*
import java.util.regex.Pattern

class Model {

    private fun getAddress(input: String): String {
        return "$QUERY_IP_HOST$input&key=$QUERY_IP_KEY"
    }

    fun sendRequest(input: String,callback : HttpUtil.Callback){
        HttpUtil.sendOkHttpRequest(getAddress(input),callback)
    }

    fun handleIpDataResponse(response: Response): IpBean? {
        try {

            val s = response.body!!.string()
            if (TextUtils.isEmpty(s)) {
                return null
            }
            val ipBean = IpBean()
            val jsonObject = JSONObject(s)
            if (jsonObject.getString("resultcode") != "200") {
                return null
            }
            val result = jsonObject.getJSONObject("result")
            ipBean.country = result.getString("Country")
            ipBean.province = result.getString("Province")
            ipBean.city = result.getString("City")
            ipBean.isp = result.getString("Isp")
            return ipBean
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun validateIp(input: String?): Boolean {
        var tempInput = input
        if (TextUtils.isEmpty(input)) {
            tempInput = SystemUtil.localIPAddress
            if (TextUtils.isEmpty(input)) {
                return false
            }
        }
        //检查ip格式
        val regex = ("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$")
        val pattern = Pattern.compile(regex)
        if(tempInput != null){
            val matcher = pattern.matcher(tempInput)
            return matcher.find()
        }
        return false
    }

    companion object {
        private const val QUERY_IP_HOST = "http://apis.juhe.cn/ip/ipNew?ip="
        private const val QUERY_IP_KEY = "ad7f15652b5f7e7799fefa92a5246d4a"
    }
}