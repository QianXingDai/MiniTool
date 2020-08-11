package com.kakacat.minitool.currencyconversion.model

import android.content.Context
import com.kakacat.minitool.R

object Rate {
    var us = 7.0847
    var eu = 7.66035
    var hk = 0.91375
    var jp = 0.063617
    var uk = 8.3778
    var au = 4.2708
    var ca = 4.93065
    var th = 0.2155
    var sg = 4.8993
    var ch = 7.21565
    var dk = 1.026
    var ma = 0.8871
    var my = 1.61385
    var no = 0.6478
    var nz = 0.415735
    var ph = 0.128965
    var ru = 0.09125
    var se = 0.7015
    var tw = 0.22375
    var br = 1.7669
    var kr = 0.002885
    var za = 0.4077
    var cn = 1.0
    fun setRate(rate: Double, num: Int) = when (num) {
        1 -> us = rate
        2 -> eu = rate
        3 -> hk = rate
        4 -> jp = rate
        5 -> uk = rate
        6 -> au = rate
        7 -> ca = rate
        8 -> th = rate
        9 -> sg = rate
        10 -> ch = rate
        11 -> dk = rate
        12 -> ma = rate
        13 -> my = rate
        14 -> no = rate
        15 -> nz = rate
        16 -> ph = rate
        17 -> ru = rate
        18 -> se = rate
        19 -> tw = rate
        20 -> br = rate
        21 -> kr = rate
        22 -> za = rate
        23 -> cn = rate
        else -> {
        }
    }

    fun getRate(num: Int): Double {
        return when (num) {
            1 -> us
            2 -> eu
            3 -> hk
            4 -> jp
            5 -> uk
            6 -> au
            7 -> ca
            8 -> th
            9 -> sg
            10 -> ch
            11 -> dk
            12 -> ma
            13 -> my
            14 -> no
            15 -> nz
            16 -> ph
            17 -> ru
            18 -> se
            19 -> tw
            20 -> br
            21 -> kr
            22 -> za
            else -> cn
        }
    }

    fun getNameId(num: Int): Int {
        return when (num) {
            1 -> R.string.name_us
            2 -> R.string.name_eu
            3 -> R.string.name_hk
            4 -> R.string.name_jp
            5 -> R.string.name_uk
            6 -> R.string.name_au
            7 -> R.string.name_ca
            8 -> R.string.name_th
            9 -> R.string.name_sg
            10 -> R.string.name_ch
            11 -> R.string.name_dk
            12 -> R.string.name_ma
            13 -> R.string.name_my
            14 -> R.string.name_no
            15 -> R.string.name_nz
            16 -> R.string.name_ph
            17 -> R.string.name_ru
            18 -> R.string.name_se
            19 -> R.string.name_tw
            20 -> R.string.name_br
            21 -> R.string.name_kr
            22 -> R.string.name_za
            else -> R.string.name_cn
        }
    }

    fun getIconId(num: Int): Int {
        return when (num) {
            1 -> R.drawable.ic_us
            2 -> R.drawable.ic_eu
            3 -> R.drawable.ic_hk
            4 -> R.drawable.ic_jp
            5 -> R.drawable.ic_uk
            6 -> R.drawable.ic_au
            7 -> R.drawable.ic_ca
            8 -> R.drawable.ic_th
            9 -> R.drawable.ic_sg
            10 -> R.drawable.ic_ch
            11 -> R.drawable.ic_dk
            12 -> R.drawable.ic_ma
            13 -> R.drawable.ic_my
            14 -> R.drawable.ic_no
            15 -> R.drawable.ic_nz
            16 -> R.drawable.ic_ph
            17 -> R.drawable.ic_ru
            18 -> R.drawable.ic_se
            19 -> R.drawable.ic_tw
            20 -> R.drawable.ic_br
            21 -> R.drawable.ic_kr
            22 -> R.drawable.ic_za
            else -> R.drawable.ic_cn
        }
    }

    fun getMoneyTypeId(num: Int): Int {
        return when (num) {
            1 -> R.string.unit_us
            2 -> R.string.unit_eu
            3 -> R.string.unit_hk
            4 -> R.string.unit_jp
            5 -> R.string.unit_uk
            6 -> R.string.unit_au
            7 -> R.string.unit_ca
            8 -> R.string.unit_th
            9 -> R.string.unit_sg
            10 -> R.string.unit_ch
            11 -> R.string.unit_dk
            12 -> R.string.unit_ma
            13 -> R.string.unit_my
            14 -> R.string.unit_no
            15 -> R.string.unit_nz
            16 -> R.string.unit_ph
            17 -> R.string.unit_ru
            18 -> R.string.unit_se
            19 -> R.string.unit_tw
            20 -> R.string.unit_br
            21 -> R.string.unit_kr
            22 -> R.string.unit_za
            else -> R.string.unit_cn
        }
    }

    fun writeRateToLocal(context: Context) {
        val sharedPreferences = context.getSharedPreferences("rate", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("us", us.toFloat())
        editor.putFloat("eu", eu.toFloat())
        editor.putFloat("hk", hk.toFloat())
        editor.putFloat("jp", jp.toFloat())
        editor.putFloat("uk", uk.toFloat())
        editor.putFloat("au", au.toFloat())
        editor.putFloat("ca", ca.toFloat())
        editor.putFloat("th", th.toFloat())
        editor.putFloat("sg", sg.toFloat())
        editor.putFloat("ch", ch.toFloat())
        editor.putFloat("dk", dk.toFloat())
        editor.putFloat("ma", ma.toFloat())
        editor.putFloat("my", my.toFloat())
        editor.putFloat("no", no.toFloat())
        editor.putFloat("nz", nz.toFloat())
        editor.putFloat("ph", ph.toFloat())
        editor.putFloat("ru", ru.toFloat())
        editor.putFloat("se", se.toFloat())
        editor.putFloat("tw", tw.toFloat())
        editor.putFloat("br", br.toFloat())
        editor.putFloat("kr", kr.toFloat())
        editor.putFloat("za", za.toFloat())
        editor.apply()
    }
}