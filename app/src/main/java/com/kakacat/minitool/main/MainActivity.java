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
import com.kakacat.minitool.main.adapter.FragmentAdapter;
import com.kakacat.minitool.main.navigation.AboutViewItemOn;
import com.kakacat.minitool.main.navigation.ChangeThemeDialog;

public class MainActivity extends FrescoInitActivity implements MainContract.View {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    @Override
    public void initData(){
        setPresenter(new Presenter(this));
        presenter.initData();
    }

    @Override
    public void initView(){
        initToolbar();

        drawerLayout = findViewById(R.id.drawer_layout);

        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(new FragmentAdapter(this,presenter));

        /*
        TODO:滑动切换fragment时，下面指示器不会跟随变化，下次再填，用tablayout可以解决，但不想用。。。
         */

        BottomNavigationView btmNav = findViewById(R.id.btm_nav);
        btmNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.daily:{
                    viewPager2.setCurrentItem(0,true);
                    break;
                }
                case R.id.geek:{
                    viewPager2.setCurrentItem(1,true);
                    break;
                }
            }
            return true;
        });
        btmNav.setSelectedItemId(R.id.daily);

        //初始化navigation
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_theme:{
                    showChangeThemeDialog();
                    break;
                }
                case R.id.nav_setting:{

                    break;
                }
                case R.id.nav_about:{
                    AboutViewItemOn aboutView = AboutViewItemOn.getInstance(this, android.view.View.inflate(this,R.layout.about_layout,null), ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    aboutView.showAtLocation(navigationView,Gravity.CENTER,0,0);
                    break;
                }
                case R.id.nav_exit:{
                    finish();
                    break;
                }
                default:
                    break;
            }
            item.setChecked(false);
            return true;
        });
    }

    public void showChangeThemeDialog(){
        ChangeThemeDialog changeThemeDialog = ChangeThemeDialog.getInstance(this, View.inflate(this,R.layout.select_theme,null), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        changeThemeDialog.showAtLocation(navigationView, Gravity.CENTER,0,0);
    }

    private void initToolbar(){
        setSupportActionBar(findViewById(R.id.toolbar_main));
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_slide);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        if (menuItem.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START))
                drawerLayout.closeDrawer(GravityCompat.START);
            else
                drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
