package com.kakacat.minitool.common.util

import android.app.Activity
import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.BatteryManager
import android.os.Vibrator
import android.util.Log
import java.io.DataOutputStream
import java.io.File
import java.io.IOException
import java.net.Inet4Address
import java.net.NetworkInterface
import java.net.SocketException

object SystemUtil {

    @JvmStatic
    fun log(log: String = "hello world") {
        Log.d("hhh", log)
    }

    @JvmStatic
    fun modifyDpi(`val`: String) {
        val commands = arrayOf(
                "wm density $`val`\n"
        )
        executeLinuxCommand(commands, true)
    }

    @JvmStatic
    fun executeLinuxCommand(commands: Array<String>, needRoot: Boolean = false, waitFor: Boolean = false) {
        try {
            if (needRoot) {
                val process = Runtime.getRuntime().exec("su")
                val os = DataOutputStream(process.outputStream)
                for (command in commands) {
                    os.writeBytes(command)
                }
                os.flush()
                if (waitFor) {
                    process.waitFor()
                }
            } else {
                val runtime = Runtime.getRuntime()
                for (cmd in commands) {
                    runtime.exec(arrayOf("/bin/sh", "-c", cmd))
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    fun createFile(file: File, needRoot: Boolean = false,waitFor: Boolean = true) {
        val commands: Array<String>
        if (file.isDirectory) {
            commands = arrayOf("mkdir ${file.absolutePath}")
            executeLinuxCommand(commands, needRoot, waitFor)
        } else {
            val parentFile = file.parentFile!!
            commands = arrayOf("mkdir ${parentFile.absolutePath}")
            executeLinuxCommand(commands, needRoot, waitFor)
            file.createNewFile()
        }
    }

    @JvmStatic
    fun getDataFormClipBoard(context: Context): CharSequence {
        val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        return clipboardManager.primaryClip!!.getItemAt(0).text
    }

    @JvmStatic
    fun copyToClipboard(context: Context, label: String?, content: CharSequence?) {
        val cm = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        cm.setPrimaryClip(ClipData.newPlainText(label, content))
    }

    @JvmStatic
    fun getElectricity(context: Context): Int {
        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
        batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
    }

    @JvmStatic
    fun setBatteryLevel(`val`: String) {
        val commands = arrayOf(
                "dumpsys battery set level $`val`\n"
        )
        executeLinuxCommand(commands, true)
    }

    @JvmStatic
    fun resetBattery() {
        val commands = arrayOf(
                """
                    dumpsys battery reset

                    """.trimIndent()
        )
        executeLinuxCommand(commands, true)
    }

    fun vibrate(context: Context, milliseconds: Long) {
        val vibrator = context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(milliseconds)
    }

    @JvmStatic
    fun openMarket(context: Context) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse("market://details?id=" + context.packageName)
        context.startActivity(intent)
    }

    fun openAppDetailInSetting(context: Context) {
        val intent = Intent()
        intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        intent.data = Uri.parse("package:" + context.packageName)
        context.startActivity(intent)
    }

    fun openAppDetailInSetting(activity: Activity, packageName: String) {
        val intent = Intent()
        intent.action = "android.settings.APPLICATION_DETAILS_SETTINGS"
        intent.data = Uri.parse("package:$packageName")
        activity.startActivity(intent)
    }


    val localIPAddress: String?
        get() {
            try {
                val mEnumeration = NetworkInterface.getNetworkInterfaces()
                while (mEnumeration.hasMoreElements()) {
                    val inf = mEnumeration.nextElement()
                    val enumIPAddress = inf.inetAddresses
                    while (enumIPAddress.hasMoreElements()) {
                        val inertAddress = enumIPAddress.nextElement()
                        if (inertAddress is Inet4Address && !inertAddress.isLoopbackAddress()) {
                            return inertAddress.getHostAddress()
                        }
                    }
                }
            } catch (e: SocketException) {
                e.printStackTrace()
            }
            return null
        }
}