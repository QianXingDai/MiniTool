package com.kakacat.minitool.main;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.base.FrescoInitActivity;
import com.kakacat.minitool.main.fragment.DailyFragment;
import com.kakacat.minitool.main.fragment.GeekFragment;
import com.kakacat.minitool.main.fragment.MyFragment;
import com.kakacat.minitool.main.navigation.AboutViewItemOn;
import com.kakacat.minitool.main.navigation.ChangeThemeView;

public class MainActivity extends FrescoInitActivity implements MainContract.View {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private DailyFragment dailyFragment;
    private GeekFragment geekFragment;
    private MyFragment currentFragment;

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

        //初始化fragment
        dailyFragment = new DailyFragment(presenter);
        geekFragment = new GeekFragment(presenter);

        //初始化bottomnavigation
        BottomNavigationView btmNav = findViewById(R.id.btm_nav);
        btmNav.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.daily:{
                    switchFragment(dailyFragment);
                    break;
                }
                case R.id.geek:{
                    switchFragment(geekFragment);
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
                    ChangeThemeView changeThemeView = ChangeThemeView.getInstance(this, android.view.View.inflate(this,R.layout.select_theme,null), ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    changeThemeView.showAtLocation(navigationView, Gravity.CENTER,0,0);
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
    public void switchFragment(MyFragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if(!fragment.isAdded()){
            transaction.add(R.id.fragment_container,fragment);
        }
        if(currentFragment != null){
            transaction.hide(currentFragment);
        }

        transaction.show(fragment);
        transaction.commit();

        currentFragment = fragment;
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
