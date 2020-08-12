package com.kakacat.minitool.wifipasswordview.model

import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class WiFiConfigSAXHandle(private val wifiList: MutableList<Wifi>) : DefaultHandler() {

    private val sb by lazy { StringBuilder() }
    private var wifi: Wifi? = null
    private var startGetName = false
    private var startGetPwd = false

    override fun startElement(uri: String, localName: String, qName: String, attributes: Attributes) {
        if (qName == "string") {
            when(attributes.getValue(0)){
                "SSID" -> {
                    wifi = Wifi()
                    startGetName = true
                }
                "PreSharedKey" -> {
                    startGetPwd = true
                }
            }
        }
    }

    override fun endElement(uri: String, localName: String, qName: String) {
        if (startGetName) { //如果已经开始获得name,那么这时候已经获取完了name,去掉首尾的引号
            var name = sb.toString()
            name = name.substring(1, name.length - 1)
            wifi!!.wifiName = name
            wifi!!.wifiImage = name.substring(0, 1)
            startGetName = false
            sb.delete(0, sb.length)
        } else if (startGetPwd) { //如果已经开始获得pwd,那么这时候已经获取完了pwd,去掉首尾的引号
            var pwd = sb.toString()
            pwd = pwd.substring(1, pwd.length - 1)
            wifi!!.wifiPwd = pwd
            wifiList.add(wifi!!)
            sb.delete(0, sb.length)
            startGetPwd = false
            wifi = null
        }
    }

    override fun characters(ch: CharArray, start: Int, length: Int) {
        if (startGetName) {
            sb.append(ch, start, length)
        } else if (startGetPwd) {
            sb.append(ch, start, length)
        }
    }
}