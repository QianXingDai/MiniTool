package com.kakacat.minitool.currencyconversion.model

import android.content.Context
import android.text.TextUtils
import com.kakacat.minitool.R
import com.kakacat.minitool.common.util.HttpUtil
import okhttp3.Response
import org.json.JSONObject

class Model {

    private val countryBeanList: MutableList<CountryBean> = ArrayList()

    init {
        fillCountryList()
    }

    fun getCountryList() = countryBeanList

    fun readRateFromLocal(context: Context) {
        val sharedPreferences = context.getSharedPreferences("rate", Context.MODE_PRIVATE)
        Rate.us = sharedPreferences.getFloat("us", Rate.us.toFloat()).toDouble()
        Rate.eu = sharedPreferences.getFloat("eu", Rate.eu.toFloat()).toDouble()
        Rate.hk = sharedPreferences.getFloat("hk", Rate.hk.toFloat()).toDouble()
        Rate.jp = sharedPreferences.getFloat("jp", Rate.jp.toFloat()).toDouble()
        Rate.uk = sharedPreferences.getFloat("uk", Rate.uk.toFloat()).toDouble()
        Rate.au = sharedPreferences.getFloat("au", Rate.au.toFloat()).toDouble()
        Rate.ca = sharedPreferences.getFloat("ca", Rate.ca.toFloat()).toDouble()
        Rate.th = sharedPreferences.getFloat("th", Rate.th.toFloat()).toDouble()
        Rate.sg = sharedPreferences.getFloat("sg", Rate.sg.toFloat()).toDouble()
        Rate.ch = sharedPreferences.getFloat("ch", Rate.ch.toFloat()).toDouble()
        Rate.dk = sharedPreferences.getFloat("dk", Rate.dk.toFloat()).toDouble()
        Rate.ma = sharedPreferences.getFloat("ma", Rate.ma.toFloat()).toDouble()
        Rate.my = sharedPreferences.getFloat("my", Rate.my.toFloat()).toDouble()
        Rate.no = sharedPreferences.getFloat("no", Rate.no.toFloat()).toDouble()
        Rate.nz = sharedPreferences.getFloat("nz", Rate.nz.toFloat()).toDouble()
        Rate.ph = sharedPreferences.getFloat("ph", Rate.ph.toFloat()).toDouble()
        Rate.ru = sharedPreferences.getFloat("ru", Rate.ru.toFloat()).toDouble()
        Rate.se = sharedPreferences.getFloat("se", Rate.se.toFloat()).toDouble()
        Rate.tw = sharedPreferences.getFloat("tw", Rate.tw.toFloat()).toDouble()
        Rate.br = sharedPreferences.getFloat("br", Rate.br.toFloat()).toDouble()
        Rate.kr = sharedPreferences.getFloat("kr", Rate.kr.toFloat()).toDouble()
        Rate.za = sharedPreferences.getFloat("za", Rate.za.toFloat()).toDouble()
    }

    fun sendRequest(): Response? {
        return HttpUtil.sendRequest(REQUEST_ADDRESS)
    }

    fun handleResponse(response: Response?): Boolean {
        if(!response!!.isSuccessful){
            return false
        }
        val s = response.body!!.string()
        return if (TextUtils.isEmpty(s)) {
            false
        } else {
            val jsonObject = JSONObject(s)
            val result = jsonObject.getJSONArray("result").getJSONObject(0)
            for (i in 1 until countryBeanList.size) {
                val data = result.getJSONObject("data$i")
                val rate = data.getString("bankConversionPri").toDouble() / 100
                Rate.setRate(rate, i)
            }
            true
        }
    }

    fun getResult(`val`: CharSequence, rate1: Double, rate2: Double): String {
        if (TextUtils.isEmpty(`val`)) {
            return ""
        }
        var s = `val`.toString().toDouble()
        s = s * rate1 / rate2
        return s.toString()
    }

    private fun fillCountryList() {
        countryBeanList.add(CountryBean(R.drawable.ic_us, R.string.name_us, R.string.unit_us, Rate.us))
        countryBeanList.add(CountryBean(R.drawable.ic_eu, R.string.name_eu, R.string.unit_eu, Rate.eu))
        countryBeanList.add(CountryBean(R.drawable.ic_hk, R.string.name_hk, R.string.unit_hk, Rate.hk))
        countryBeanList.add(CountryBean(R.drawable.ic_jp, R.string.name_jp, R.string.unit_jp, Rate.jp))
        countryBeanList.add(CountryBean(R.drawable.ic_uk, R.string.name_uk, R.string.unit_uk, Rate.uk))
        countryBeanList.add(CountryBean(R.drawable.ic_au, R.string.name_au, R.string.unit_au, Rate.au))
        countryBeanList.add(CountryBean(R.drawable.ic_ca, R.string.name_ca, R.string.unit_ca, Rate.ca))
        countryBeanList.add(CountryBean(R.drawable.ic_th, R.string.name_th, R.string.unit_th, Rate.th))
        countryBeanList.add(CountryBean(R.drawable.ic_sg, R.string.name_sg, R.string.unit_sg, Rate.sg))
        countryBeanList.add(CountryBean(R.drawable.ic_ch, R.string.name_ch, R.string.unit_ch, Rate.ch))
        countryBeanList.add(CountryBean(R.drawable.ic_dk, R.string.name_dk, R.string.unit_dk, Rate.dk))
        countryBeanList.add(CountryBean(R.drawable.ic_ma, R.string.name_ma, R.string.unit_ma, Rate.ma))
        countryBeanList.add(CountryBean(R.drawable.ic_my, R.string.name_my, R.string.unit_my, Rate.my))
        countryBeanList.add(CountryBean(R.drawable.ic_no, R.string.name_no, R.string.unit_no, Rate.no))
        countryBeanList.add(CountryBean(R.drawable.ic_nz, R.string.name_nz, R.string.unit_nz, Rate.nz))
        countryBeanList.add(CountryBean(R.drawable.ic_ph, R.string.name_ph, R.string.unit_ph, Rate.ph))
        countryBeanList.add(CountryBean(R.drawable.ic_ru, R.string.name_ru, R.string.unit_ru, Rate.ru))
        countryBeanList.add(CountryBean(R.drawable.ic_se, R.string.name_se, R.string.unit_se, Rate.se))
        countryBeanList.add(CountryBean(R.drawable.ic_tw, R.string.name_tw, R.string.unit_tw, Rate.tw))
        countryBeanList.add(CountryBean(R.drawable.ic_br, R.string.name_br, R.string.unit_br, Rate.br))
        countryBeanList.add(CountryBean(R.drawable.ic_kr, R.string.name_kr, R.string.unit_kr, Rate.kr))
        countryBeanList.add(CountryBean(R.drawable.ic_za, R.string.name_za, R.string.unit_za, Rate.za))
        countryBeanList.add(CountryBean(R.drawable.ic_cn, R.string.name_cn, R.string.unit_cn, Rate.cn))
    }

    companion object {
        private const val EXCHANGE_RATE_HOST = "http://web.juhe.cn:8080/finance/exchange/rmbquot?key="
        private const val EXCHANGE_RATE_KEY = "6103d9a9aeca9c09fc8f6bd4734be680"
        private const val REQUEST_ADDRESS = EXCHANGE_RATE_HOST + EXCHANGE_RATE_KEY
    }
}