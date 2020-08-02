package com.kakacat.minitool.bingpic;

import android.graphics.Bitmap;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kakacat.minitool.common.util.UiUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Model {

    //必应查询接口
    private static final String BING_PIC_HOST = "https://bing.ioliu.cn/v1/rand";

    private static final int DEFAULT_LOAD_NUM = 10;
    private static final int REQUEST_IMAGE_WIDTH = 320;
    private static final int REQUEST_IMAGE_HEIGHT = 240;

    private static Model model;

    private List<String> addressList;
    private int index;

    private Model() {

    }

    public static Model getInstance() {
        if (model == null) {
            model = new Model();
        }
        return model;
    }

    public void initData() {
        addressList = new ArrayList<>();
    }

    public void loadMore() {
        addressList = getAddressList();
        for (int i = 0; i < DEFAULT_LOAD_NUM; i++) {
            addressList.add(getNextAddress());
        }
    }

    public String saveImage(SimpleDraweeView imageView) {
        String result;
        try {
            String path = "/storage/emulated/0/Pictures/MiniTool/" + System.currentTimeMillis() + ".png";
            File file = new File(path);
            File parentFile = file.getParentFile();
            assert parentFile != null;
            if (!parentFile.exists())
                parentFile.mkdirs();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(path);
            Bitmap bitmap = UiUtil.drawableToBitmap(imageView.getDrawable());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);

            fos.flush();
            fos.close();

            result = "保存成功";
        } catch (IOException e) {
            e.printStackTrace();
            result = "保存失败";
        }
        return result;
    }

    private String getNextAddress() {
        return BING_PIC_HOST +
                "?d=" + (index++) +
                "?w=" + REQUEST_IMAGE_WIDTH +
                "height" + REQUEST_IMAGE_HEIGHT;
    }

    public List<String> getAddressList() {
        if (addressList == null) {
            addressList = new ArrayList<>();
        }
        return addressList;
    }
}
