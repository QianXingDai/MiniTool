package com.kakacat.minitool.wifipasswordview;

import android.content.Context;

import com.kakacat.minitool.common.util.SystemUtil;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import bolts.Continuation;
import bolts.Task;

public class Presenter implements Contract.Presenter {

    private Contract.View view;
    private Context context;

    private List<Wifi> wifiList;

    public Presenter(Contract.View view) {
        this.view = view;
        this.context = view.getContext();
    }

    @Override
    public void initData(){
        Task.callInBackground(() -> {
            String result = "获取wifi配置信息失败,请检查是否有ROOT权限";
            wifiList = getWifiList();
            if(getWifiConfig()){
                result =  "获取wifi配置文件成功,但是处理失败";
                String filePath = Objects.requireNonNull(context.getExternalCacheDir()).getAbsolutePath() + "/WifiConfigStore.xml";
                try{
                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser parser = factory.newSAXParser();
                    parser.parse(new File(filePath),new WiFiConfigSAXHandle(wifiList));
                    result = "解析成功";
                } catch (ParserConfigurationException | IOException | SAXException e) {
                    e.printStackTrace();
                }
            }
            return result;
        }).onSuccess((Continuation<String, Void>) task -> {
            view.onGetWifiDataCallBack(task.getResult());
            return null;
        },Task.UI_THREAD_EXECUTOR);
    }

    private boolean getWifiConfig(){
        try{
            String fileName = "/data/misc/wifi/WifiConfigStore.xml";
            String cacheDir = Objects.requireNonNull(context.getExternalCacheDir()).getAbsolutePath();
            String[] commands = new String[]{
                    "chmod 777 " + fileName + "\n",
                    "cp " + fileName + " " + cacheDir + "\n",
                    "exit\n"
            };
            SystemUtil.executeLinuxCommand(commands,true,true);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Wifi> getWifiList(){
        if(wifiList == null){
            wifiList = new ArrayList<>();
        }
        return wifiList;
    }
}
