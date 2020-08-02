package com.kakacat.minitool.main;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.base.FrescoInitActivity;
import com.kakacat.minitool.common.ui.DepthPageTransformer;
import com.kakacat.minitool.main.adapter.FragmentAdapter;
import com.kakacat.minitool.main.navigation.AboutViewItemOn;
import com.kakacat.minitool.main.navigation.ChangeThemeDialog;

public class MainActivity extends FrescoInitActivity implements MainContract.View {

    private MainContract.Presenter presenter;
    private DrawerLayout drawerLayout;
    private ViewPager2 viewPager2;
    private BottomNavigationView btmNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        presenter = null;
        super.onDestroy();
    }

    @Override
    public void initData() {
        getPresenter().initData();
    }

    @Override
    public void initView() {
        initToolbar();

        drawerLayout = findViewById(R.id.drawer_layout);
        btmNav = findViewById(R.id.btm_nav);
        viewPager2 = findViewById(R.id.fragment_container);
        //这一句解决出错后不恢复原fragment
        viewPager2.setSaveEnabled(false);
        viewPager2.setPageTransformer(new DepthPageTransformer());
        viewPager2.setAdapter(new FragmentAdapter(this, presenter));
        viewPager2.registerOnPageChangeCallback(getOnPageChangeCallback());
        btmNav.setOnNavigationItemSelectedListener(getBottomOnNavigationItemSelectedListener());
        btmNav.setSelectedItemId(R.id.daily);
        //初始化navigation
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(getOnNavigationItemSelectedListener());
    }

    @Override
    public MainContract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new Presenter(this);
        }
        return presenter;
    }

    @Override
    public void showChangeThemeDialog() {
        ChangeThemeDialog changeThemeDialog = ChangeThemeDialog.getInstance(this, View.inflate(this, R.layout.select_theme, null), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        changeThemeDialog.showAtLocation(drawerLayout, Gravity.CENTER, 0, 0);
    }

    @Override
    public void initToolbar() {
        super.initToolbar();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_slide);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
        return true;
    }

    @Override
    public Context getContext() {
        return this;
    }

    private ViewPager2.OnPageChangeCallback getOnPageChangeCallback() {
        return new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    btmNav.setSelectedItemId(R.id.daily);
                } else if (position == 1) {
                    btmNav.setSelectedItemId(R.id.geek);
                }
            }
        };
    }

    private BottomNavigationView.OnNavigationItemSelectedListener getBottomOnNavigationItemSelectedListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.daily: {
                    viewPager2.setCurrentItem(0, true);
                    break;
                }
                case R.id.geek: {
                    viewPager2.setCurrentItem(1, true);
                    break;
                }
            }
            return true;
        };
    }

    private NavigationView.OnNavigationItemSelectedListener getOnNavigationItemSelectedListener() {
        return item -> {
            //TODO:这里功能基本不完整
            switch (item.getItemId()) {
                case R.id.nav_theme: {
                    showChangeThemeDialog();
                    break;
                }
                case R.id.nav_setting: {

                    break;
                }
                case R.id.nav_about: {
                    AboutViewItemOn aboutView = AboutViewItemOn.getInstance(this, android.view.View.inflate(this, R.layout.about_layout, null), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    aboutView.showAtLocation(drawerLayout, Gravity.CENTER, 0, 0);
                    break;
                }
                case R.id.nav_exit: {
                    finish();
                    break;
                }
                default:
                    break;
            }
            item.setChecked(false);
            return true;
        };
    }
}
