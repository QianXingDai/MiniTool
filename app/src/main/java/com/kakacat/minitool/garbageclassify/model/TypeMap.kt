package com.kakacat.minitool.garbageclassify.model

import com.kakacat.minitool.R

object TypeMap {

    fun getIcon(type: Int): Int {
        when (type) {
            0 -> return R.drawable.ic_recycleable
            1 -> return R.drawable.ic_harmful
            2 -> return R.drawable.ic_wet
            3 -> return R.drawable.ic_dry
        }
        return 0
    }

    fun getTypeName(type: Int): String {
        when (type) {
            0 -> return "可回收垃圾"
            1 -> return "有害垃圾"
            2 -> return "厨余(湿)垃圾"
            3 -> return "其他(干)垃圾"
        }
        return ""
    }
}