package com.kakacat.minitool.textencryption

import com.kakacat.minitool.common.util.EncryptionUtil.encryptBASE64
import com.kakacat.minitool.common.util.EncryptionUtil.encryptionMD5
import com.kakacat.minitool.common.util.StringUtil.byteToString
import javax.crypto.Mac
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class Model {

    fun encryptText(content: String, method: CharSequence): String? {
        return if (method == encryptionMethods[0]) {
            encryptionMD5(content.toByteArray(), false)
        } else if (method == encryptionMethods[1]){
            encryptBASE64(content.toByteArray())
        } else if (method == encryptionMethods[2]){
            encryptHmacSHA1(content.toByteArray())
        }else{
            null
        }
    }

    private fun encryptHmacSHA1(encryptText: ByteArray): String? {
        try {
            val encryptKey = encryptBASE64(encryptText)
            val data = encryptKey.toByteArray()
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            val secretKey: SecretKey = SecretKeySpec(data, "HmacSHA1")
            //生成一个指定 Mac 算法 的 Mac 对象
            val mac = Mac.getInstance("HmacSHA1")
            //用给定密钥初始化 Mac 对象
            mac.init(secretKey)
            //完成 Mac 操作
            val bytes = mac.doFinal(encryptText)
            return byteToString(bytes, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    companion object {
        val encryptionMethods = arrayOf("BASE64", "MD5", "HmacSHA1")
    }
}