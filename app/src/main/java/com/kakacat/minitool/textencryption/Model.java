package com.kakacat.minitool.textencryption;

import com.kakacat.minitool.common.util.EncryptionUtil;
import com.kakacat.minitool.common.util.StringUtil;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Model {

    private static final String[] encryptionMethods = {"BASE64", "MD5", "HmacSHA1"};

    private static Model model;

    private Model() {

    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public String[] getEncryptionMethods() {
        return encryptionMethods;
    }

    public String encryptText(String content, CharSequence method) {
        String result = null;
        if (method.equals(encryptionMethods[0])) {
            result = EncryptionUtil.encryptionMD5(content.getBytes(), false);
        } else if (method.equals(encryptionMethods[1]))
            result = EncryptionUtil.encryptBASE64(content.getBytes());
        else if (method.equals(encryptionMethods[2]))
            result = encryptHmacSHA1(content.getBytes());
        return result;
    }

    private String encryptHmacSHA1(byte[] encryptText) {
        try {
            String encryptKey = EncryptionUtil.encryptBASE64(encryptText);
            byte[] data = encryptKey.getBytes();
            //根据给定的字节数组构造一个密钥,第二参数指定一个密钥算法的名称
            SecretKey secretKey = new SecretKeySpec(data, "HmacSHA1");
            //生成一个指定 Mac 算法 的 Mac 对象
            Mac mac = Mac.getInstance("HmacSHA1");
            //用给定密钥初始化 Mac 对象
            mac.init(secretKey);
            //完成 Mac 操作
            byte[] bytes = mac.doFinal(encryptText);
            return StringUtil.byteToString(bytes, false);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
