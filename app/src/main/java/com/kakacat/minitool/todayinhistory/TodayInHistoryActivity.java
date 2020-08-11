package com.kakacat.minitool.todayinhistory;

import android.app.DatePickerDialog;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.base.BaseActivity;
import com.kakacat.minitool.common.util.UiUtil;

public class TodayInHistoryActivity extends BaseActivity implements Contract.View {

    private Contract.Presenter presenter;
    private DatePickerDialog datePickerDialog;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);

        initData();
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.refreshData();
    }

    @Override
    public void initData() {
        presenter = new Presenter(this);
        presenter.initData();
    }

    @Override
    public void initView() {
        UiUtil.setTranslucentStatusBarWhite(this);
        UiUtil.initToolbar(this,true);

        MyListView listView = findViewById(R.id.list_view);
        adapter = new Adapter(this, R.layout.article_layout, presenter.getArticleList());
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showCalendarDialog());
    }

    @Override
    public void onUpdateDataCallBack(String result, boolean needRefresh) {
        if (needRefresh) {
            adapter.notifyDataSetChanged();
        }
        UiUtil.showToast(getContext(), result);
    }

    @Override
    public void showCalendarDialog() {
        if (datePickerDialog == null) {
            datePickerDialog = new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {
                presenter.setMonth(monthOfYear + 1);
                presenter.setDay(dayOfMonth);
                presenter.refreshData();
            }, presenter.getYear(), presenter.getMonth() - 1, presenter.getDay());
        }
        datePickerDialog.show();
    }
}
