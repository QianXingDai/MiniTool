package com.kakacat.minitool.common.util

import android.icu.text.SimpleDateFormat

object StringUtil {
    fun byteToMegabyte(bytes: Long): String {
        val result: String
        result = if (bytes > 1024 * 1024 * 1024) {
            val temp = (bytes / (1024 * 1024 * 1024.0)).toString()
            temp.substring(0, temp.indexOf('.') + 2) + "G"
        } else {
            val temp = (bytes / (1024 * 1024.0)).toString()
            temp.substring(0, temp.indexOf('.') + 2) + "M"
        }
        return result
    }

    @JvmStatic
    fun getDate(time: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(time)
    }

    @JvmStatic
    fun byteToString(bytes: ByteArray, addColon: Boolean): String {
        val sb = StringBuilder()
        for (i in bytes.indices) {
            if (Integer.toHexString(0xFF and bytes[i].toInt()).length == 1) sb.append("0").append(Integer.toHexString(0xFF and bytes[i].toInt())) else sb.append(Integer.toHexString(0xFF and bytes[i].toInt()))
            if (addColon) if (i != bytes.size - 1) sb.append(':')
        }
        return sb.toString()
    }
}