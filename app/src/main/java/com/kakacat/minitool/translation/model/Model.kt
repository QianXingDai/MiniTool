package com.kakacat.minitool.translation.model

import android.content.Context
import android.content.SharedPreferences
import android.text.TextUtils
import com.kakacat.minitool.common.util.EncryptionUtil.encryptionMD5
import com.kakacat.minitool.common.util.HttpUtil
import com.kakacat.minitool.translation.model.LanguageMap.getShortCode
import okhttp3.Response
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList

class Model {

    val languageList1: List<String> by lazy {
        val languageList1 = ArrayList<String>()
        Collections.addAll(languageList1, *LanguageMap.languages)
        languageList1
    }
    val languageList2: List<String> by lazy {
        val languageList2 = ArrayList<String>()
        languageList2.addAll(listOf(*LanguageMap.languages).subList(1, LanguageMap.languages.size))
        languageList2
    }
    lateinit var collectionList: MutableList<String>
    private var mSharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    fun initData() {
        languageList1
        languageList2
    }

    private fun readCollectionFromLocal(context: Context): MutableList<String> {
        if (!this::collectionList.isInitialized) {
            collectionList = ArrayList()
            val map = getSharedPreferences(context)!!.all as Map<String, String>
            collectionList.addAll(map.keys)
        }
        return collectionList
    }

    fun validateInput(input: String?): Boolean{
        if(input == null || TextUtils.isEmpty(input.trim())){
            return false
        }
        return true
    }

    fun addToMyFavourite(source: String, target: String, context: Context) {
        editor = getEditor(context)
        val s = "$source > $target"
        editor!!.putString(s, s)
        editor!!.commit()
    }

    fun sendRequest(input: String, from: CharSequence, to: CharSequence): Response? {
        val address = getAddress(input, from, to)
        return HttpUtil.sendRequest(address)
    }

    private fun getAddress(input: String, from: CharSequence, to: CharSequence): String {
        var from: CharSequence? = from
        var to: CharSequence? = to
        from = getShortCode(from!!)
        to = getShortCode(to!!)
        val random: String = ((Math.random() * 1000000)).toString()
        val s = TRANSLATE_APP_ID + input + random + TRANSLATE_SECRET_KEY
        val sign = encryptionMD5(s.toByteArray(), false)
        return TRANSLATE_HOST +
                "q=" + input +
                "&from=" + from +
                "&to=" + to +
                "&appid=" + TRANSLATE_APP_ID +
                "&salt=" + random +
                "&sign=" + sign
    }

    fun handleTranslationResponse(response: Response?): String? {
        try {
            if(response == null || !response.isSuccessful || response.body == null){
                return null
            }
            val s = response.body!!.string()
            val jsonObject = JSONObject(s)
            val jsonObject1 = jsonObject.getJSONArray("trans_result").getJSONObject(0)
            return jsonObject1.getString("dst")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getSharedPreferences(context: Context): SharedPreferences? {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getSharedPreferences("MyFavourite", Context.MODE_PRIVATE)
            mSharedPreferences!!.registerOnSharedPreferenceChangeListener { sharedPreferences: SharedPreferences, key: String? -> sharedPreferences.getString(key, "")?.let { collectionList.add(it) } }
        }
        return mSharedPreferences
    }

    private fun getEditor(context: Context): SharedPreferences.Editor? {
        if (editor == null) {
            editor = getSharedPreferences(context)!!.edit()
        }
        return editor
    }

    companion object {
        //百度提供的翻译查询接口appid
        private const val TRANSLATE_APP_ID = "20200420000425201"
        private const val TRANSLATE_SECRET_KEY = "qceN7y1RBpEp8x1g47_i"

        //百度提供的翻译查询接口
        private const val TRANSLATE_HOST = "https://api.fanyi.baidu.com/api/trans/vip/translate?"
    }
}