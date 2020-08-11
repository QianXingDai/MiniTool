package com.kakacat.minitool.common.util

import android.content.pm.Signature
import android.util.Base64
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object EncryptionUtil {
    /**
     * MD5加密
     *
     * @param byteStr 需要加密的内容
     * @return 返回 byteStr的md5值
     */
    @JvmStatic
    fun encryptionMD5(byteStr: ByteArray?, addColon: Boolean): String {
        val messageDigest: MessageDigest
        var result = ""
        try {
            messageDigest = MessageDigest.getInstance("MD5")
            messageDigest.reset()
            messageDigest.update(byteStr!!)
            val byteArray = messageDigest.digest()
            result = StringUtil.byteToString(byteArray, addColon)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return result
    }

    /**
     * 获取app签名md5值,与“keytool -list -keystore D:\Desktop\app_key”‘keytool -printcert     *file D:\Desktop\CERT.RSA’获取的md5值一样
     */
    @JvmStatic
    fun getSignMd5Str(signatures: Signature): String {
        return encryptionMD5(signatures.toByteArray(), true)
    }

    @JvmStatic
    fun encryptBASE64(key: ByteArray?): String {
        return Base64.encodeToString(key, Base64.DEFAULT)
    }
}