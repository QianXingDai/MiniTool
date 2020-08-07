package com.kakacat.minitool.bingpic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.ui.RecycleViewScrollListener;
import com.kakacat.minitool.common.ui.view.MyPopupWindow;
import com.kakacat.minitool.common.util.SystemUtil;
import com.kakacat.minitool.common.util.UiUtil;

public class BingPicActivity extends AppCompatActivity implements Contract.View, View.OnClickListener {

    private static final int REQUEST_PERMISSION_CODE = 2;

    private Contract.Presenter presenter;

    private CoordinatorLayout coordinatorLayout;
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
        getPresenter().initData();
    }

    @Override
    public void initView() {
        UiUtil.setTranslucentStatusBarWhite(this);
        UiUtil.initToolbar(this,true);

        coordinatorLayout = findViewById(R.id.coordinator_layout);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new ImageAdapter(presenter.getAddressList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.addOnScrollListener(new RecycleViewScrollListener(() -> presenter.loadMore()));

        adapter.setOnClickListener((v, position) -> showBigImage(v));
        adapter.setOnLongClickListener((v, position) -> showOptionDialog(v));
        adapter.setOnTouchListener((v, event) -> {
            currentX = (int) event.getRawX();
            currentY = (int) event.getRawY();
        });
    }

    @Override
    public Contract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new Presenter(this);
        }
        return presenter;
    }

    @Override
    public void showBigImage(View view) {
        currentImageView = view.findViewById(R.id.image_view);
        Drawable contentDrawable = currentImageView.getDrawable();
        if (bigImageDialog == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.big_image_layout, coordinatorLayout, false);
            bigImageDialog = new MyPopupWindow(this, contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            bigImageView = contentView.findViewById(R.id.image_view);
        }
        bigImageView.setImageDrawable(contentDrawable);
        bigImageDialog.showAtLocation(coordinatorLayout, Gravity.CENTER, 0, 0);
    }

    @Override
    public void showOptionDialog(View view) {
        currentImageView = view.findViewById(R.id.image_view);
        if (optionDialog == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.option_layout, coordinatorLayout, false);
            optionDialog = new MyPopupWindow(this, contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            optionDialog.setAlpha(1.0f);
        }
        optionDialog.showAtLocation(coordinatorLayout, Gravity.NO_GRAVITY, currentX, currentY);
    }

    //TODO：保存图片会失败，获取到的图片高宽为0，不知道怎么肥四。。
    @Override
    public void saveImage() {
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (checkSelfPermission(permissions[0]) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(permissions, REQUEST_PERMISSION_CODE);
        } else {
            presenter.saveImage(currentImageView);
        }
    }

    @Override
    public void onSaveImageCallBack(String result) {
        optionDialog.dismiss();
        UiUtil.showToast(this, result);
        SystemUtil.vibrate(this, 50);
    }

    @Override
    public void onUpdateImagesCallBack() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
            UiUtil.dismissLoadingWindow();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                UiUtil.showToast(this, "权限获取失败,后期会导致保存图片失败,其他功能不受影响");
            } else {
                presenter.saveImage(currentImageView);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab: {
 //               UiUtil.slideUpToTop(nestedScrollView);
                break;
            }
            case R.id.tv_save_image: {
                saveImage();
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return false;
    }
}
