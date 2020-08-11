package com.kakacat.minitool.appInfo.model

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import com.kakacat.minitool.R
import com.kakacat.minitool.appInfo.model.bean.ApiPercentBean
import com.kakacat.minitool.appInfo.model.bean.AppInfoBean
import java.util.*
import java.util.function.Consumer

class AppInfoModel {

    private var apiPercentBeanList: MutableList<ApiPercentBean>? = null
    private var packageInfoList: List<PackageInfo>? = null
    private var appInfoBeanList: MutableList<AppInfoBean>? = null

    fun initData(pm: PackageManager?) {
        fun initApiPercentData(){
            apiPercentBeanList = getApiPercentBeanList()
            packageInfoList = getPackageInfoList(pm!!)
            val apiCount = IntArray(icons.size)
            packageInfoList!!.forEach(Consumer { packageInfo: PackageInfo -> apiCount[packageInfo.applicationInfo.targetSdkVersion - 1]++ })
            for (i in apiCount.indices.reversed()) {
                if (apiCount[i] != 0) {
                    var s = (apiCount[i] * 100.0 / packageInfoList!!.size).toString()
                    s = s.substring(0, s.indexOf('.') + 2) + "%"
                    val model = ApiPercentBean(icons[i], versionName[i], "API " + (i + 1), apiCount[i], s)
                    apiPercentBeanList!!.add(model)
                }
            }
        }
        fun initAppInfoData(){
            appInfoBeanList = getAppInfoBeanList()
            packageInfoList = getPackageInfoList(pm!!)
            packageInfoList!!.forEach(Consumer { packageInfo: PackageInfo ->
                val appInfoBean = AppInfoBean()
                appInfoBean.targetSdkVersion = packageInfo.applicationInfo.targetSdkVersion
                appInfoBean.icon = packageInfo.applicationInfo.loadIcon(pm)
                appInfoBean.appName = packageInfo.applicationInfo.loadLabel(pm).toString()
                appInfoBean.packageName = packageInfo.packageName
                appInfoBean.versionName = packageInfo.versionName
                appInfoBean.androidVersionName = versionName[appInfoBean.targetSdkVersion - 1]
                appInfoBean.minSdkVersion = packageInfo.applicationInfo.minSdkVersion
                appInfoBean.firstInstallTime = packageInfo.firstInstallTime
                appInfoBean.lastUpdateTime = packageInfo.lastUpdateTime
                appInfoBean.setSignature(packageInfo.signatures[0])
                appInfoBean.setPermissions(packageInfo.requestedPermissions)
                appInfoBean.sourceDir = packageInfo.applicationInfo.sourceDir
                appInfoBeanList!!.add(appInfoBean)
            })
        }
        initApiPercentData()
        initAppInfoData()
    }

    fun getApiPercentBeanList(): MutableList<ApiPercentBean> {
        if (apiPercentBeanList == null) {
            apiPercentBeanList = ArrayList()
        }
        return apiPercentBeanList!!
    }

    private fun getPackageInfoList(pm : PackageManager): List<PackageInfo> {
        if (packageInfoList == null) {
            packageInfoList = pm.getInstalledPackages(PackageManager.GET_SIGNATURES or PackageManager.GET_PERMISSIONS)
        }
        return packageInfoList!!
    }

    fun getAppInfoBeanList(): MutableList<AppInfoBean> {
        if (appInfoBeanList == null) {
            appInfoBeanList = ArrayList()
        }
        return appInfoBeanList!!
    }

    fun sortAppInfoList(sortFlag: Int) {
        appInfoBeanList?.sortWith(Comparator sort@{ o1: AppInfoBean, o2: AppInfoBean ->
            when(sortFlag){
                SORT_BY_APP_NAME -> return@sort o2.appName.compareTo(o1.appName)
                SORT_BY_TARGET_API -> return@sort o2.targetSdkVersion - o1.targetSdkVersion
                SORT_BY_MIN_API -> return@sort o2.minSdkVersion - o1.minSdkVersion
                SORT_BY_FIRST_INSTALL_TIME -> return@sort (o2.firstInstallTime - o1.firstInstallTime).toInt()
                SORT_BY_LAST_UPDATE_TIME -> return@sort (o2.lastUpdateTime - o1.lastUpdateTime).toInt()
            }
            return@sort 0
        })
    }

    companion object {
        const val SORT_BY_APP_NAME = 1
        const val SORT_BY_TARGET_API = 2
        const val SORT_BY_MIN_API = 3
        const val SORT_BY_FIRST_INSTALL_TIME = 4
        const val SORT_BY_LAST_UPDATE_TIME = 5

        // API对应关系
        private val versionName = arrayOf("1.0", "1.1", "Cupcake", "Donut", "Eclair",
                "Eclair", "Eclair", "Froyo", "Gingerbread", "Gingerbread",
                "Honeycomb", "Honeycomb", "Honeycomb", "Ice Cream Sandwich", "Ice Cream Sandwich",
                "Jelly Bean", "Jelly Bean", "Jelly Bean", "KitKat", "KitKat",
                "Lollipop", "Lollipop", "Marshmallow", "Nougat", "Nougat",
                "Oreo", "Oreo", "Pie", "Android Q", "Android R")
        private val icons = intArrayOf(R.drawable.android_cupcake, R.drawable.android_cupcake, R.drawable.android_cupcake, R.drawable.android_donut, R.drawable.android_eclair,
                R.drawable.android_eclair, R.drawable.android_eclair, R.drawable.android_froyo, R.drawable.android_gingerbread, R.drawable.android_gingerbread,
                R.drawable.android_honeycomb, R.drawable.android_honeycomb, R.drawable.android_honeycomb, R.drawable.android_ice_cream_sandwich, R.drawable.android_ice_cream_sandwich,
                R.drawable.android_jelly_bean, R.drawable.android_jelly_bean, R.drawable.android_jelly_bean, R.drawable.android_kitkat, R.drawable.android_kitkat,
                R.drawable.android_lollipop, R.drawable.android_lollipop, R.drawable.android_marshmallow, R.drawable.android_nougat, R.drawable.android_nougat,
                R.drawable.android_oreo, R.drawable.android_oreo, R.drawable.android_pie, R.drawable.android_q, R.drawable.android_r)
    }
}