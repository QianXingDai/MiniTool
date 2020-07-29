package com.kakacat.minitool.bingpic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.base.FrescoInitActivity;
import com.kakacat.minitool.common.util.UiUtil;
import com.kakacat.minitool.common.ui.view.MyPopupWindow;
import com.kakacat.minitool.common.util.SystemUtil;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;

public class BingPicActivity extends FrescoInitActivity implements Contract.View,View.OnClickListener {

    private static final int REQUEST_PERMISSION_CODE = 2;

    private Contract.Presenter presenter;

    private NestedScrollView nestedScrollView;
    private ImageAdapter adapter;
    private MyPopupWindow bigImageDialog;
    private MyPopupWindow optionDialog;
    private ImageView bigImageView;
    private SimpleDraweeView currentImageView;

    private int currentX;
    private int currentY;

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
        adapter.setOnClickListener((v, position) -> showBigImage(v));
        adapter.setOnLongClickListener((v, position) -> showOptionDialog(v));
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

    @Override
    public void showBigImage(View view){
        currentImageView = view.findViewById(R.id.image_view);
        Drawable contentDrawable = currentImageView.getDrawable();
        if(bigImageDialog == null){
            View contentView = LayoutInflater.from(this).inflate(R.layout.big_image_layout,nestedScrollView,false);
            bigImageDialog = new MyPopupWindow(this,contentView,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
            bigImageView = contentView.findViewById(R.id.image_view);
        }
        bigImageView.setImageDrawable(contentDrawable);
        bigImageDialog.showAtLocation(nestedScrollView,Gravity.CENTER,0,0);
    }

    @Override
    public void showOptionDialog(View view){
        currentImageView = view.findViewById(R.id.image_view);
        if(optionDialog == null){
            View contentView = LayoutInflater.from(this).inflate(R.layout.option_layout,nestedScrollView,false);
            optionDialog = new MyPopupWindow(this,contentView, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            optionDialog.setAlpha(1.0f);
        }
        optionDialog.showAtLocation(nestedScrollView,Gravity.NO_GRAVITY,currentX,currentY);
    }

    //TODO：保存图片会失败，获取到的图片高宽为0，不知道怎么肥四。。
    @Override
    public void saveImage(){
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(permissions,REQUEST_PERMISSION_CODE);
        }else{
            presenter.saveImage(currentImageView);
        }
    }

    @Override
    public void onSaveImageCallBack(String result) {
        optionDialog.dismiss();
        UiUtil.showToast(this,result);
        SystemUtil.vibrate(this,50);
    }

    @Override
    public void onUpdateImagesCallBack(){
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_PERMISSION_CODE){
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED){
                UiUtil.showToast(this,"权限获取失败,后期会导致保存图片失败,其他功能不受影响");
            }else{
                presenter.saveImage(currentImageView);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab:{
                UiUtil.slideUpToTop(nestedScrollView);
                break;
            }
            case R.id.tv_save_image:{
                saveImage();
                break;
            }
        }
    }

}
