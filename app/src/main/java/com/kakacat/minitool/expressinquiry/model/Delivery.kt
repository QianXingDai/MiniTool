package com.kakacat.minitool.expressinquiry.model

import java.io.Serializable

class Delivery(private var status: String, val companyName: String, val code: String, val tel: String, var updateTime: String, var packageDetailList: List<PackageDetail>) : Serializable {
    fun getStatus(): String {
        when (status.toInt()) {
            -1 -> return "待查询"
            0 -> return "查询异常"
            1 -> return "暂无记录"
            2 -> return "在途中"
            3 -> return "在派送"
            4 -> return "已签收"
            5 -> return "用户拒签"
            6 -> return "疑难件"
            7 -> return "无效单"
            8 -> return "超时单"
            9 -> return "签收失败"
            10 -> return "退回"
        }
        return ""
    }

    fun setStatus(status: String) {
        this.status = status
    }

}