package com.kakacat.minitool.common.util;

import android.app.Activity;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Vibrator;
import android.util.Log;

import java.io.DataOutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class SystemUtil {

    public static void log(String log){
        Log.d("hhh",log);
    }

    public static void modifyDpi(String val){
        String[] commands = new String[]{
                "wm density " + val + "\n"
        };
        executeLinuxCommand(commands,true,false);
    }

    public static void executeLinuxCommand(String[] commands,boolean needRoot,boolean waitFor){
        try{
            if(needRoot){
                Process process = Runtime.getRuntime().exec("su");
                DataOutputStream os = new DataOutputStream(process.getOutputStream());
                for(String command : commands) os.writeBytes(command);
                os.flush();
                if(waitFor) process.waitFor();
            } else{
                Runtime runtime = Runtime.getRuntime();
                for(String cmd : commands){
                    runtime.exec(new String[]{"/bin/sh","-c",cmd});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void copyToClipboard(Context context, String label, CharSequence content){
        ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setPrimaryClip(ClipData.newPlainText(label,content));
    }

    public static int getElectricity(Context context) {
        BatteryManager batterymanager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
        batterymanager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return batterymanager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }

    public static void setBatteryLevel(String val){
        String[] commands = new String[]{
          "dumpsys battery set level " + val + "\n"
        };
        executeLinuxCommand(commands,true,false);
    }

    public static void resetBattery(){
        String[] commands = new String[]{
                "dumpsys battery reset" + "\n"
        };
        executeLinuxCommand(commands,true,false);
    }

    public static void vibrate(Context context, long milliseconds){
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(milliseconds);
    }

    public static void openMarket(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=" + context.getPackageName()));
        context.startActivity(intent);
    }

    public static void openAppDetailInSetting(Activity activity){
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }

    public static String getLocalIPAddress(){
        try{
            for(Enumeration<NetworkInterface> mEnumeration = NetworkInterface.getNetworkInterfaces(); mEnumeration.hasMoreElements();) {
                NetworkInterface intf = mEnumeration.nextElement();
                for(Enumeration<InetAddress> enumIPAddr = intf.getInetAddresses(); enumIPAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIPAddr.nextElement();
                    if (inetAddress instanceof Inet4Address && !inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }
}
