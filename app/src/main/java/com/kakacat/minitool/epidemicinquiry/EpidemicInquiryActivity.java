package com.kakacat.minitool.epidemicinquiry;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.base.FrescoInitActivity;
import com.kakacat.minitool.common.util.UiUtil;
import com.kakacat.minitool.epidemicinquiry.adapter.FragmentAdapter;
import com.kakacat.minitool.epidemicinquiry.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;


public class EpidemicInquiryActivity extends FrescoInitActivity implements Contract.View {

    private Contract.Presenter presenter;

    private List<String> tabNameList;
    private List<MyFragment> myFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epidemic_inquiry);

        initData();
        initView();
    }


    @Override
    public void initData() {
        setPresenter(new Presenter(this));
        presenter.initData();
    }

    @Override
    public void initView() {
        super.initToolbar();

        Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.setStatusBarColor(Color.TRANSPARENT);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager2 viewPager = findViewById(R.id.view_pager);

        getTabNameList().forEach(tabName->{
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setText(tabName);
            tabLayout.addTab(tab);
        });
        viewPager.setAdapter(new FragmentAdapter(this,getFragmentList()));
        new TabLayoutMediator(tabLayout, viewPager, true, (tab, position) -> tab.setText(tabNameList.get(position))).attach();
    }

    @Override
    public void onUpdateViewSuccessful(){
        getFragmentList().get(0).updateView();
    }

    @Override
    public void onUpdateViewError(){
        UiUtil.showToast(this,"请求失败");
    }

    private List<MyFragment> getFragmentList(){
        if(myFragmentList == null){
            myFragmentList = new ArrayList<>();
            myFragmentList.add(new MyFragment(presenter));
            myFragmentList.add(new MyFragment(presenter));
            myFragmentList.add(new MyFragment(presenter));
        }
        return myFragmentList;
    }

    private List<String> getTabNameList(){
        if(tabNameList == null){
            tabNameList = new ArrayList<>();
            tabNameList.add("国内疫情");
            tabNameList.add("国外疫情");
            tabNameList.add("实况播报");
        }
        return tabNameList;
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }
}
