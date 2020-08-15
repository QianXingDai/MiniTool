package com.kakacat.minitool.wifipasswordview

import com.kakacat.minitool.common.util.SystemUtil.copyToClipboard
import com.kakacat.minitool.wifipasswordview.model.Model
import com.kakacat.minitool.wifipasswordview.model.Wifi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Presenter(private val view: Contract.View) : Contract.Presenter {

    private val model by lazy { Model() }
    private val context by lazy { view.context }

    override fun initData() {
        GlobalScope.launch(Dispatchers.Default) {
            var result = "获取wifi配置信息失败,请检查是否有ROOT权限"
            if (model.copyWifiConfigToCache(context)) {
                result = if (model.handleWifiConfig(context)) {
                    "解析成功"
                } else {
                    "获取wifi配置文件成功,但是处理失败"
                }
            }
            val finalResult = result
            view.onGetWifiDataCallBack(finalResult)
        }
    }

    override fun copyToClipboard(position: Int) {
        val wifi = model.wifiList[position]
        copyToClipboard(context, "wifiPwd", wifi.wifiPwd)
        view.onCopyCallback("\"" + wifi.wifiName + "\"的wifi密码已复制")
    }

    override fun getWifiList(): List<Wifi>{
        return model.wifiList
    }
}