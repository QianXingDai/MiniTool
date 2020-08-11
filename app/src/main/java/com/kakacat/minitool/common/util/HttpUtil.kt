package com.kakacat.minitool.common.util

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

object HttpUtil {

    private val okHttpClient by lazy { OkHttpClient() }
    private val builder: Request.Builder by lazy { Request.Builder() }

    @JvmStatic
    @JvmOverloads
    fun sendOkHttpRequest(address: String, callback: Callback, requestBody: RequestBody? = null) {
        ThreadUtil.callInBackground(Runnable {
            val builder = builder.url(address)
            val response: Response?
            val request: Request
            request = if (requestBody != null) {
                builder.post(requestBody).build()
            } else {
                builder.get().build()
            }

            response = okHttpClient.newCall(request).execute()
            if (response.isSuccessful) {
                callback.onSuccess(response)
            } else {
                callback.onError()
            }
        })
    }

    interface Callback {
        fun onSuccess(response: Response?)
        fun onError()
    }
}