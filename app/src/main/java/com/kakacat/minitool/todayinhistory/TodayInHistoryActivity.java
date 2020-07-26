package com.kakacat.minitool.todayinhistory;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.base.FrescoInitActivity;
import com.kakacat.minitool.common.constant.Result;
import com.kakacat.minitool.common.ui.UiUtil;

import bolts.Task;

public class TodayInHistoryActivity extends FrescoInitActivity implements Contract.View {

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
    public void onResume(){
        super.onResume();
        presenter.refreshData();
    }

    @Override
    public void initData(){
        setPresenter(new Presenter(this));
        presenter.initData();
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void initView() {
        initToolbar();

        MyListView listView = findViewById(R.id.list_view);
        adapter = new Adapter(this,R.layout.article_layout,presenter.getArticleList());
        listView.setAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showCalendarDialog());
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onUpdateDataCallBack(int resultFlag){
        Task.call(() -> {
            if(resultFlag == Result.HANDLE_SUCCESS){
                adapter.notifyDataSetChanged();
            }else if(resultFlag == Result.REQUEST_ERROR){
                UiUtil.showToast(this,"请求错误");
            }else if(resultFlag == Result.HANDLE_FAIL){
                UiUtil.showToast(this,"处理响应数据失败");
            }
            return null;
        },Task.UI_THREAD_EXECUTOR);
    }

    @Override
    public void showCalendarDialog(){
        if(datePickerDialog == null){
                datePickerDialog = new DatePickerDialog(this, (datePicker, year, monthOfYear, dayOfMonth) -> {
                presenter.setMonth(monthOfYear + 1);
                presenter.setDay(dayOfMonth);
                presenter.refreshData();
            }, presenter.getYear(), presenter.getMonth() - 1, presenter.getDay());
        }
        datePickerDialog.show();
    }
}
