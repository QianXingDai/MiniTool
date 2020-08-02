package com.kakacat.minitool.cleanfile;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.kakacat.minitool.R;
import com.kakacat.minitool.cleanfile.adapter.FragmentAdapter;
import com.kakacat.minitool.cleanfile.model.MyFragment;
import com.kakacat.minitool.common.ui.view.MyPopupWindow;
import com.kakacat.minitool.common.util.SystemUtil;
import com.kakacat.minitool.common.util.UiUtil;

import java.util.ArrayList;
import java.util.List;

public class CleanFileActivity extends AppCompatActivity implements Contract.View {

    private static final int REQUEST_CODE = 1;

    private Contract.Presenter presenter;

    private CoordinatorLayout coordinatorLayout;
    private ViewPager2 viewPager2;
    private BottomNavigationView btmNav;
    private ProgressBar progressBar;
    private MaterialButton btSelectAll;
    private MyPopupWindow popupWindow;

    private List<MyFragment> myFragmentList;
    private int currentPagePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO:Snackbar弹出样式为透明...
        setContentView(R.layout.activity_clean_file);

        requestPermission();
        initView();
    }

    @Override
    public void requestPermission() {
        myFragmentList = new ArrayList<>();

        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
        List<String> requestPermissionList = new ArrayList<>();

        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionList.add(permission);
            }
        }

        if (requestPermissionList.size() > 0) {
            ActivityCompat.requestPermissions(this, requestPermissionList.toArray(new String[0]), REQUEST_CODE);
        } else {
            initData();
        }
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
        progressBar = findViewById(R.id.progress_bar);
        btSelectAll = findViewById(R.id.bt_select_all);
        viewPager2 = findViewById(R.id.fragment_container);
        btmNav = findViewById(R.id.btm_nav);

        presenter = getPresenter();
        presenter.getFileListList().forEach(list -> myFragmentList.add(new MyFragment(list)));

        btmNav.setOnNavigationItemSelectedListener(getNavigationItemSelectedListener());
        viewPager2.setAdapter(new FragmentAdapter(this, myFragmentList));
        viewPager2.registerOnPageChangeCallback(getPackChangeCallBack());
    }

    @Override
    public Contract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new Presenter(this);
        }
        return presenter;
    }

    private ViewPager2.OnPageChangeCallback getPackChangeCallBack() {
        return new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPagePosition = position;
                switch (position) {
                    case 0: {
                        if (myFragmentList.get(0).isSelectedAll()) {
                            btSelectAll.setBackgroundResource(R.drawable.ic_clear);
                        } else {
                            btSelectAll.setBackgroundResource(R.drawable.ic_select_all);
                        }
                        btmNav.setSelectedItemId(R.id.big_file);
                        break;
                    }
                    case 1: {
                        if (myFragmentList.get(1).isSelectedAll()) {
                            btSelectAll.setBackgroundResource(R.drawable.ic_clear);
                        } else {
                            btSelectAll.setBackgroundResource(R.drawable.ic_select_all);
                        }
                        btmNav.setSelectedItemId(R.id.empty_file);
                        break;
                    }
                    case 2: {
                        if (myFragmentList.get(2).isSelectedAll()) {
                            btSelectAll.setBackgroundResource(R.drawable.ic_clear);
                        } else {
                            btSelectAll.setBackgroundResource(R.drawable.ic_select_all);
                        }
                        btmNav.setSelectedItemId(R.id.apk);
                        break;
                    }
                    case 3: {
                        if (myFragmentList.get(3).isSelectedAll()) {
                            btSelectAll.setBackgroundResource(R.drawable.ic_clear);
                        } else {
                            btSelectAll.setBackgroundResource(R.drawable.ic_select_all);
                        }
                        btmNav.setSelectedItemId(R.id.video);
                        break;
                    }
                    case 4: {
                        if (myFragmentList.get(4).isSelectedAll()) {
                            btSelectAll.setBackgroundResource(R.drawable.ic_clear);
                        } else {
                            btSelectAll.setBackgroundResource(R.drawable.ic_select_all);
                        }
                        btmNav.setSelectedItemId(R.id.audio);
                        break;
                    }
                    default:
                        break;
                }
            }
        };
    }

    private BottomNavigationView.OnNavigationItemSelectedListener getNavigationItemSelectedListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.big_file: {
                    viewPager2.setCurrentItem(0, true);
                    break;
                }
                case R.id.empty_file: {
                    viewPager2.setCurrentItem(1, true);
                    break;
                }
                case R.id.apk: {
                    viewPager2.setCurrentItem(2, true);
                    break;
                }
                case R.id.video: {
                    viewPager2.setCurrentItem(3, true);
                    break;
                }
                case R.id.audio: {
                    viewPager2.setCurrentItem(4, true);
                    break;
                }
                default:
                    break;
            }
            return true;
        };
    }

    @Override
    public void onUpdateDataCallBack() {
        progressBar.setVisibility(View.INVISIBLE);
        viewPager2.setVisibility(View.VISIBLE);
        myFragmentList.forEach(myFragment -> myFragment.getAdapter().notifyDataSetChanged());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE) {
            int count = 0;
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    count++;
                    UiUtil.showToast(this, "获取存储权限失败,请手动打开存储权限哟");
                    SystemUtil.openAppDetailInSetting(this);
                }
            }
            if (count == 0) {
                initData();
            }
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_select_all: {
                selectAll();
                break;
            }
            case R.id.fab_delete: {
                showDialogWindow();
                break;
            }
            case R.id.bt_delete_file: {
                popupWindow.dismiss();
                presenter.deleteSelectedFile();
                break;
            }
            case R.id.bt_cancel: {
                popupWindow.dismiss();
                break;
            }
        }
    }

    @Override
    public void selectAll() {
        MyFragment myFragment = myFragmentList.get(currentPagePosition);
        myFragment.setSelectedAll(!myFragment.isSelectedAll(), btSelectAll);
        presenter.selectAll(currentPagePosition, myFragmentList.get(currentPagePosition).isSelectedAll());
    }

    @Override
    public void onSelectedAllCallBack() {
        myFragmentList.get(currentPagePosition).getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onFileDeletedCallBack(String result) {
        myFragmentList.forEach(myFragment -> myFragment.getAdapter().notifyDataSetChanged());
        //TODO:透明的,不知道为啥。。。
        UiUtil.showSnackBar(coordinatorLayout, result);
    }

    @Override
    public void showDialogWindow() {
        if (popupWindow == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.dialog, viewPager2, false);
            popupWindow = new MyPopupWindow(this, view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        popupWindow.showAtLocation(viewPager2, Gravity.CENTER, 0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
