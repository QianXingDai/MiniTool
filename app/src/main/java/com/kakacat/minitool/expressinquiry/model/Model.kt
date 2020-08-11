package com.kakacat.minitool.expressinquiry.model

import android.content.Context
import android.text.TextUtils
import androidx.annotation.IntRange
import com.google.gson.Gson
import com.kakacat.minitool.common.util.HttpUtil
import com.kakacat.minitool.common.util.HttpUtil.sendOkHttpRequest
import okhttp3.FormBody
import okhttp3.RequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*
import java.util.stream.Collectors

class Model {

    private lateinit var unSignedList: MutableList<Delivery>
    private lateinit var signedList: MutableList<Delivery>
    private lateinit var allList: MutableList<Delivery>

    fun initData(context: Context) {
        loadDataFromLocal(context)
        unSignedList = ArrayList()
        signedList = ArrayList()
        allList = ArrayList()
    }

    fun sendRequest(code: String, callback: HttpUtil.Callback) {
        val address = HOST +
                "?key=" + KEY +
                "&number=" + code
        val requestBody: RequestBody = FormBody.Builder()
                .add("key", KEY)
                .add("code", code)
                .build()
        sendOkHttpRequest(address, callback, requestBody)
    }

    fun validateInput(code: String): Boolean {
        if(TextUtils.isEmpty(code)){
            return false
        }
        for (ch in code.toCharArray()) {
            if (ch < '0' || ch > '9') {
                return false
            }
        }
        return true
    }

    fun isInSignedList(code: String): Boolean {
        for (delivery in signedList) {
            if (delivery.code == code) {
                return true
            }
        }
        return false
    }

    fun handleResponse(response: Response, code: String): Boolean {
        val delivery = handle(response, code) ?: return false
        var flag = false
        for (delivery1 in allList) {
            if (delivery1.code == code) {
                flag = true
                delivery1.setStatus(delivery.getStatus())
                delivery1.packageDetailList = delivery.packageDetailList
                delivery1.updateTime = delivery.updateTime
            }
        }
        if (!flag) {
            allList.add(delivery)
            if (delivery.getStatus() == "已签收") {
                signedList.add(delivery)
            } else {
                unSignedList.add(delivery)
            }
        } else {
            unSignedList.clear()
            unSignedList.addAll(allList.stream().filter { delivery1: Delivery -> delivery1.getStatus() != "已签收" }.collect(Collectors.toList()))
            signedList.clear()
            signedList.addAll(allList.stream().filter { delivery1: Delivery -> delivery1.getStatus() == "已签收" }.collect(Collectors.toList()))
        }
        return true
    }

    private fun handle(response: Response, code: String): Delivery? {
        val s = response.body!!.string()
        val jsonObject = JSONObject(s)
        val jsonObject1 = jsonObject.getJSONArray("newslist").getJSONObject(0)
        val status = jsonObject1.getString("status")
        val updateTime = jsonObject1.getString("updatetime")
        val companyName = jsonObject1.getString("kuaidiname")
        val tel = jsonObject1.getString("telephone")
        val jsonArray = jsonObject1.getJSONArray("list")
        val packageDetailList: MutableList<PackageDetail> = ArrayList()
        val gson = Gson()
        for (i in 0 until jsonArray.length()) {
            val `object` = jsonArray.getJSONObject(i)
            packageDetailList.add(gson.fromJson(`object`.toString(), PackageDetail::class.java))
        }
        return Delivery(status, companyName, code, tel, updateTime, packageDetailList)
    }

    fun saveDataToLocal(context: Context) {
        val oos1 = ObjectOutputStream(context.openFileOutput("delivery_unsigned", Context.MODE_PRIVATE))
        val oos2 = ObjectOutputStream(context.openFileOutput("delivery_signed", Context.MODE_PRIVATE))
        val oos3 = ObjectOutputStream(context.openFileOutput("delivery_all", Context.MODE_PRIVATE))

        oos1.writeObject(unSignedList)
        oos2.writeObject(signedList)
        oos3.writeObject(allList)

        oos1.close()
        oos2.close()
        oos3.close()
    }

    private fun loadDataFromLocal(context: Context) {
        val ois1 = ObjectInputStream(context.openFileInput("delivery_unsigned"))
        val ois2 = ObjectInputStream(context.openFileInput("delivery_signed"))
        val ois3 = ObjectInputStream(context.openFileInput("delivery_all"))

        unSignedList = ois1.readObject() as MutableList<Delivery>
        signedList = ois2.readObject() as MutableList<Delivery>
        allList = ois3.readObject() as MutableList<Delivery>

        ois1.close()
        ois2.close()
        ois3.close()
    }

    fun getList(@IntRange(from = 0, to = 2) position: Int): MutableList<Delivery>? {
        return when(position){
            0 -> unSignedList
            1 -> signedList
            2 -> allList
            else -> null
        }
    }

    companion object {
        private const val KEY = "2675f5858d0ba338b6d8d4f93cfe17be"
        private const val HOST = "http://api.tianapi.com/txapi/kuaidi/index"
    }
}