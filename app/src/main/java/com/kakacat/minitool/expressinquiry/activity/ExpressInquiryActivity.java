package com.kakacat.minitool.expressinquiry.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.ui.DepthPageTransformer;
import com.kakacat.minitool.common.ui.view.MyPopupWindow;
import com.kakacat.minitool.common.util.UiUtil;
import com.kakacat.minitool.expressinquiry.Contract;
import com.kakacat.minitool.expressinquiry.MyFragment;
import com.kakacat.minitool.expressinquiry.Presenter;
import com.kakacat.minitool.expressinquiry.adapter.FragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class ExpressInquiryActivity extends AppCompatActivity implements Contract.View {

    private Contract.Presenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewPager2 viewPager2;
    private BottomNavigationView btmNav;
    private MyPopupWindow ppwQuery;
    private List<MyFragment> myFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_express_inquiry);

        initData();
        initView();
    }

    @Override
    protected void onDestroy() {
        presenter.saveData();
        presenter = null;
        super.onDestroy();
    }

    @Override
    public void initData() {
        presenter = getPresenter();
        presenter.initData();
    }

    @Override
    public void initView() {
        UiUtil.setTranslucentStatusBarBlack(this);
        UiUtil.initToolbar(this,true);

        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout);
        viewPager2 = findViewById(R.id.fragment_container);
        btmNav = findViewById(R.id.btm_nav);

        viewPager2.setPageTransformer(new DepthPageTransformer());
        viewPager2.registerOnPageChangeCallback(getOnPageChangeCallback());
        viewPager2.setAdapter(new FragmentAdapter(this, getMyFragmentList()));
        btmNav.setOnNavigationItemSelectedListener(getBottomOnNavigationItemSelectedListener());
        btmNav.setSelectedItemId(R.id.unsigned);
        swipeRefreshLayout.setOnRefreshListener(() -> presenter.refreshAll());
    }

    @Override
    public void onRequestCallback(String result, boolean needRefresh) {
        UiUtil.showToast(this, result);
        if (needRefresh) {
            myFragmentList.forEach(myFragment -> myFragment.getAdapter().notifyDataSetChanged());
        }
        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
        if (ppwQuery != null && ppwQuery.isShowing()) {
            ppwQuery.dismiss();
        }
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void showWindows(View fab) {
        if (ppwQuery == null) {
            View view = LayoutInflater.from(this).inflate(R.layout.input_code, swipeRefreshLayout, false);
            ppwQuery = new MyPopupWindow(this, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            EditText editText = view.findViewById(R.id.et_code);
            Button button = view.findViewById(R.id.btn);

            editText.setOnLongClickListener(v -> {
                CharSequence data = presenter.getDataFromClipBoard();
                editText.setText(editText.getText().toString() + data);
                return true;
            });
            button.setOnClickListener(v -> {
                String code = editText.getText().toString();
                presenter.requestData(code);
                editText.setText("");
            });
        }
        ppwQuery.showAtLocation(swipeRefreshLayout, Gravity.CENTER, 0, 0);
    }

    private ViewPager2.OnPageChangeCallback getOnPageChangeCallback() {
        return new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                if (position == 0) {
                    btmNav.setSelectedItemId(R.id.unsigned);
                } else if (position == 1) {
                    btmNav.setSelectedItemId(R.id.signed);
                } else if (position == 2) {
                    btmNav.setSelectedItemId(R.id.all);
                }
            }
        };
    }

    private BottomNavigationView.OnNavigationItemSelectedListener getBottomOnNavigationItemSelectedListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.unsigned: {
                    viewPager2.setCurrentItem(0, true);
                    break;
                }
                case R.id.signed: {
                    viewPager2.setCurrentItem(1, true);
                    break;
                }
                case R.id.all: {
                    viewPager2.setCurrentItem(2, true);
                }
            }
            return true;
        };
    }

    private List<MyFragment> getMyFragmentList() {
        if (myFragmentList == null) {
            myFragmentList = new ArrayList<>();
            myFragmentList.add(new MyFragment(presenter.getDeliveryList(0)));
            myFragmentList.add(new MyFragment(presenter.getDeliveryList(1)));
            myFragmentList.add(new MyFragment(presenter.getDeliveryList(2)));
        }
        return myFragmentList;
    }

    @Override
    public Contract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new Presenter(this);
        }
        return presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}