package com.kakacat.minitool.appInfo.model

import android.app.Activity
import android.graphics.Bitmap
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean
import com.kakacat.minitool.common.util.SystemUtil
import java.io.File
import java.io.FileOutputStream

class AppDetailModel {

    private var appInfoBean: AppInfoBean?=null

    fun getAppInfoBean(activity: Activity): AppInfoBean? {
        when(appInfoBean){
            null -> appInfoBean = activity.intent.getParcelableExtra("appInfo")
        }
        return appInfoBean
    }

    fun saveIcon(): String {
        val result: String
        val path = "/storage/emulated/0/MiniTool/" + appInfoBean!!.appName + ".png"
        val file = File(path)
        SystemUtil.createFile(file,false)
        val fos = FileOutputStream(file)
        appInfoBean!!.bitmap!!.compress(Bitmap.CompressFormat.PNG,100,fos)
        fos.flush()
        fos.close()
        result = "成功保存在目录$path"
        return result
    }

    fun openMarket(activity: Activity){
        SystemUtil.openMarket(activity)
    }

    fun saveApk(): String{
        val srcPath = appInfoBean!!.sourceDir
        val desPath = "/storage/emulated/0/MiniTool/${appInfoBean!!.appName}.apk"
        SystemUtil.createFile(File(desPath),false)
        val commands = arrayOf("cp $srcPath $desPath \n")
        SystemUtil.executeLinuxCommand(commands,false,true)
        return "提取成功,保存在$desPath"
    }

    fun copyMd5(activity: Activity): String{
        SystemUtil.copyToClipboard(activity,"md5",appInfoBean!!.signMd5)
        return "复制成功"
    }

    fun openDetailInSetting(activity: Activity?) {
        SystemUtil.openAppDetailInSetting(activity!!, appInfoBean!!.packageName)
    }
}