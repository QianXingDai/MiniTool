package com.kakacat.minitool.bingpic;

import android.graphics.Bitmap;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kakacat.minitool.common.constant.Host;
import com.kakacat.minitool.common.util.UiUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Presenter implements Contract.Presenter {

    private static final int DEFAULT_LOAD_NUM = 10;
    private static final int REQUEST_IMAGE_WIDTH = 320;
    private static final int REQUEST_IMAGE_HEIGHT = 240;

    private Contract.View view;
    private List<String> addressList;
    private int index;

    public Presenter(Contract.View view) {
        this.view = view;
    }

    @Override
    public void initData(){
        addressList = new ArrayList<>();
        loadMore();
    }

    @Override
    public void loadMore(){
        for(int i = 0; i < DEFAULT_LOAD_NUM; i++){
            addressList.add(getNextAddress());
        }
        view.onUpdateImagesCallBack();
    }

    @Override
    public void saveImage(SimpleDraweeView imageView){
        try{
            String path = "/storage/emulated/0/Pictures/MiniTool/" + System.currentTimeMillis() + ".png";
            File file = new File(path);
            File parentFile = file.getParentFile();
            assert parentFile != null;
            if(!parentFile.exists())
                parentFile.mkdirs();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(path);
            Bitmap bitmap = UiUtil.drawableToBitmap(imageView.getDrawable());
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);

            fos.flush();
            fos.close();

            view.onSaveImageCallBack("保存成功");
        }catch (IOException e){
            e.printStackTrace();
            view.onSaveImageCallBack("保存失败");
        }
    }

    private String getNextAddress(){
        return Host.BING_PIC_HOST +
                "?d=" + (index++) +
                "?w=" + REQUEST_IMAGE_WIDTH +
                "height" + REQUEST_IMAGE_HEIGHT;
    }

    @Override
    public List<String> getAddressList() {
        return addressList;
    }
}
