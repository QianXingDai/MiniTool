package com.kakacat.minitool.common.util;

import android.icu.text.SimpleDateFormat;

public class StringUtil {

    public static String byteToMegabyte(long bytes){
        String result;
        if(bytes > 1024 * 1024 * 1024){
            String temp = String.valueOf(bytes / (1024 * 1024 * 1024.0));
            result = temp.substring(0,temp.indexOf('.') + 2) + "G";
        }else{
            String temp = String.valueOf(bytes / (1024 * 1024.0));
            result = temp.substring(0,temp.indexOf('.') + 2) + "M";
        }
        return result;
    }

    public static String getDate(long time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(time);
    }

    public static String byteToString(byte[] bytes,boolean addColon){
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < bytes.length; i++) {
            if (Integer.toHexString(0xFF & bytes[i]).length() == 1)
                sb.append("0").append(Integer.toHexString(0xFF & bytes[i]));
            else
                sb.append(Integer.toHexString(0xFF & bytes[i]));
            if(addColon)
                if(i != bytes.length - 1)
                    sb.append(':');
        }
        return sb.toString();
    }


}
