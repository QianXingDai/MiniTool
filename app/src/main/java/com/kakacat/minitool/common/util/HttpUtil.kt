package com.kakacat.minitool.common.util

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

object HttpUtil {

    private val okHttpClient by lazy { OkHttpClient() }
    private val builder: Request.Builder by lazy { Request.Builder() }

    //同步请求数据
    fun sendRequest(address: String, requestBody: RequestBody? = null): Response?{
        val builder = builder.url(address)
        val request = if (requestBody != null) {
            builder.post(requestBody).build()
        } else {
            builder.get().build()
        }
        return okHttpClient.newCall(request).execute()
    }
}