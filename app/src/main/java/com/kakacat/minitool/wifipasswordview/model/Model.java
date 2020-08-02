package com.kakacat.minitool.wifipasswordview.model;

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

public class Model {

    private static Model model;

    private List<Wifi> wifiList;

    private Model() {

    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public boolean handleWifiConfig(Context context) {
        String filePath = Objects.requireNonNull(context.getExternalCacheDir()).getAbsolutePath() + "/WifiConfigStore.xml";
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(new File(filePath), new WiFiConfigSAXHandle(wifiList));
            return true;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean copyWifiConfigToCache(Context context) {
        try {
            String fileName = "/data/misc/wifi/WifiConfigStore.xml";
            String cacheDir = Objects.requireNonNull(context.getExternalCacheDir()).getAbsolutePath();
            String[] commands = new String[]{
                    "chmod 777 " + fileName + "\n",
                    "cp " + fileName + " " + cacheDir + "\n",
                    "exit\n"
            };
            SystemUtil.executeLinuxCommand(commands, true, true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Wifi> getWifiList() {
        if (wifiList == null) {
            wifiList = new ArrayList<>();
        }
        return wifiList;
    }
}
