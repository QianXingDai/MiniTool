package com.kakacat.minitool.bingpic

import android.graphics.Bitmap
import com.facebook.drawee.view.SimpleDraweeView
import com.kakacat.minitool.common.util.SystemUtil
import com.kakacat.minitool.common.util.UiUtil.drawable2Bitmap
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class Model {

    private var addressList: MutableList<String>? = null
    private var index = 0

    fun initData() {
        addressList = ArrayList()
    }

    fun loadMore() {
        addressList = getAddressList()
        for (i in 0 until DEFAULT_LOAD_NUM) {
            addressList!!.add(nextAddress)
        }
    }

    fun saveImage(imageView: SimpleDraweeView): String {
        val result: String
        result = try {
            val path = "/storage/emulated/0/Pictures/" + System.currentTimeMillis() + ".jpeg"
            val file = File(path)
            SystemUtil.createFile(file, false)
            val fos = FileOutputStream(path)
            val bitmap = drawable2Bitmap(imageView.drawable)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            fos.flush()
            fos.close()
            "保存成功"
        } catch (e: IOException) {
            e.printStackTrace()
            "保存失败"
        }
        return result
    }

    private val nextAddress: String
        get() = BING_PIC_HOST +
                "?d=" + index++ +
                "?w=" + REQUEST_IMAGE_WIDTH +
                "height" + REQUEST_IMAGE_HEIGHT

    fun getAddressList(): MutableList<String> {
        if (addressList == null) {
            addressList = ArrayList()
        }
        return addressList!!
    }

    companion object {
        //必应查询接口
        private const val BING_PIC_HOST = "https://bing.ioliu.cn/v1/rand"
        private const val DEFAULT_LOAD_NUM = 10
        private const val REQUEST_IMAGE_WIDTH = 320
        private const val REQUEST_IMAGE_HEIGHT = 240
    }
}