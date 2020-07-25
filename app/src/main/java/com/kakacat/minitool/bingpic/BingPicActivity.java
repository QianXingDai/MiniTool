package com.kakacat.minitool.bingpic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.base.FrescoInitActivity;
import com.kakacat.minitool.common.ui.UiUtil;
import com.kakacat.minitool.common.ui.view.MyPopupWindow;
import com.kakacat.minitool.common.util.SystemUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

public class BingPicActivity extends FrescoInitActivity implements Contract.View,View.OnClickListener {

    private static final int REQUEST_PERMISSION_CODE = 2;

    private OptionPopupWindow myPopupWindow;
    private View popupView;
    private ImageView bigImageView;
    private NestedScrollView nestedScrollView;
    private ImageAdapter adapter;

    private int currentX;
    private int currentY;

    private Contract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bing_pic);

        initData();
        initView();
    }


    @Override
    public void initData() {
        setPresenter(new Presenter(this));
        presenter.initData();
    }

    @Override
    public void initView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new ImageAdapter(presenter.getAddressList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(newState == SCROLL_STATE_IDLE){
                    Fresco.getImagePipeline().resume();
                }else if(newState == SCROLL_STATE_DRAGGING){
                    Fresco.getImagePipeline().pause();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
        adapter.setOnClickListener((v, position) -> {
            ImageView imageView = v.findViewById(R.id.image_view);
            if(popupView == null){
                popupView = LayoutInflater.from(this).inflate(R.layout.big_image_layout,null,false);
                bigImageView = popupView.findViewById(R.id.image_view);
            }
            if(myPopupWindow == null){
                myPopupWindow = new OptionPopupWindow(this,popupView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                myPopupWindow.setAlpha(0.0f);
            }
            Drawable drawable = imageView.getDrawable();
            if(drawable != null){
                bigImageView.setImageDrawable(drawable);
                myPopupWindow.showAtLocation(recyclerView, Gravity.CENTER,0,0);
            }
        });
        adapter.setOnLongClickListener((v, position) -> {
            OptionPopupWindow optionPopupWindow = OptionPopupWindow.getInstance(this,R.layout.option_layout, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            requestPermissions();
            optionPopupWindow.showAtLocation(v,Gravity.NO_GRAVITY,currentX,currentY);
        });
        adapter.setOnTouchListener((v, event) -> {
            currentX = (int) event.getRawX();
            currentY = (int) event.getRawY();
            return false;
        });

        nestedScrollView = findViewById(R.id.nested_scroll_view);
        nestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            View view = nestedScrollView.getChildAt(0);
            int bottom = view.getBottom();
            bottom -= nestedScrollView.getHeight() + nestedScrollView.getScrollY();
            if(bottom == 0){
                presenter.loadMore();
            }
        });
    }

    public void onUpdateImages(){
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void requestPermissions(){
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(permissions,REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION_CODE){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                UiUtil.showToast(this,"权限获取失败,后期会导致保存图片失败,其他功能不受影响");
            }
        }
    }

    @Override
    public void onClick(View view) {
        UiUtil.slideUpToTop(nestedScrollView);
    }

    static class OptionPopupWindow extends MyPopupWindow {

        private static OptionPopupWindow optionPopupWindow;
        private Context context;
        private View contentView;
        private ImageView imageView;

        OptionPopupWindow(Context context, View contentView, int width, int height) {
            super(context, contentView, width, height);
        }

        static OptionPopupWindow getInstance(Context context,int resId, int width, int height){
            if(optionPopupWindow == null){
                View contentView = View.inflate(context,resId,null);
                optionPopupWindow = new OptionPopupWindow(context,contentView,width,height);
                optionPopupWindow.context = context;
                optionPopupWindow.contentView = contentView;
                optionPopupWindow.setAlpha(1.0f);
                optionPopupWindow.initView();
            }
            return optionPopupWindow;
        }

        public void showAtLocation(View parent, int gravity, int x, int y) {
            super.showAtLocation(parent, gravity, x, y);
            this.imageView = parent.findViewById(R.id.image_view);
        }

        private void initView(){
            TextView tvSaveImage = contentView.findViewById(R.id.tv_save_image);
            tvSaveImage.setOnClickListener(v -> saveImage());
        }

        private void saveImage(){
            Log.d("hhh","saveImage");
            try{
                String path = "/storage/emulated/0/Pictures/MiniTool/" + System.currentTimeMillis() + ".png";
                File file = new File(path);
                File parentFile = file.getParentFile();
                if(!parentFile.exists())
                    parentFile.mkdirs();
                file.createNewFile();
                FileOutputStream fos = new FileOutputStream(path);
                Bitmap bitmap = UiUtil.drawableToBitmap(imageView.getDrawable());
                bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);

                Log.d("hhh",imageView.getDrawable().hashCode() + "");

                fos.flush();
                fos.close();

                dismiss();
                SystemUtil.vibrate(context,50);
                Toast.makeText(context,"保存成功",Toast.LENGTH_SHORT).show();
            }catch (IOException e){
                e.printStackTrace();
                Toast.makeText(context,"保存失败",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
