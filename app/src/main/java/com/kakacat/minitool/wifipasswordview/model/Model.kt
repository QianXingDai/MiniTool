package com.kakacat.minitool.wifipasswordview.model

import android.content.Context
import com.kakacat.minitool.common.util.SystemUtil.executeLinuxCommand
import java.io.File
import javax.xml.parsers.SAXParserFactory

class Model {

    val wifiList: MutableList<Wifi> by lazy { ArrayList<Wifi>() }

    fun handleWifiConfig(context: Context): Boolean {
        val filePath = context.externalCacheDir!!.absolutePath + "/WifiConfigStore.xml"
        try {
            val factory = SAXParserFactory.newInstance()
            val parser = factory.newSAXParser()
            parser.parse(File(filePath), WiFiConfigSAXHandle(wifiList))
            return true
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }

    fun copyWifiConfigToCache(context: Context): Boolean {
        return try {
            val fileName = "/data/misc/wifi/WifiConfigStore.xml"
            val cacheDir = context.externalCacheDir!!.absolutePath
            val commands = arrayOf(
                    "chmod 777 $fileName\n",
                    "cp $fileName $cacheDir\n",
                    "exit\n"
            )
            executeLinuxCommand(commands, needRoot = true, waitFor = true)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}