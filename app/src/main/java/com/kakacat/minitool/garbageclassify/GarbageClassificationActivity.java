package com.kakacat.minitool.garbageclassify;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.kakacat.minitool.R;
import com.kakacat.minitool.common.constant.Result;
import com.kakacat.minitool.common.ui.UiUtil;
import com.kakacat.minitool.common.ui.view.MyPopupWindow;
import com.kakacat.minitool.garbageclassify.model.Garbage;
import com.kakacat.minitool.garbageclassify.model.TypeMap;

import java.util.concurrent.Callable;

import bolts.Task;

public class GarbageClassificationActivity extends AppCompatActivity implements Contract.View{

    private Contract.Presenter presenter;
    private LinearLayout linearLayout;
    private View contentView;
    private MyPopupWindow loadingDialog;
    private MyPopupWindow contentDialog;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garbage_classification);

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle("垃圾分类查询");

        linearLayout = findViewById(R.id.linear_layout);
        TextInputLayout til = findViewById(R.id.til);
        EditText editText = findViewById(R.id.edit_text);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        adapter = new MyAdapter(presenter.getGarbageList());
        adapter.setOnClickListener((v, position) -> showContentView(position));
        til.setStartIconOnClickListener(view -> requestData(editText.getText().toString()));
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void requestData(String s){
        if(loadingDialog == null){
            View view = LayoutInflater.from(this).inflate(R.layout.loading_view,linearLayout,false);
            loadingDialog = new MyPopupWindow(this,view, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        loadingDialog.showAtLocation(linearLayout, Gravity.CENTER,0,0);
        presenter.requestData(s);
    }

    @Override
    public void onRequestCallBack(int resultFlag) {
        Task.call((Callable<Void>) () -> {
            loadingDialog.dismiss();
            if(resultFlag == Result.HANDLE_SUCCESS){
                adapter.notifyDataSetChanged();
                UiUtil.showToast(this,"查询成功");
            }else{
                UiUtil.showToast(this,"输入错误或者查找不到结果");
            }
            return null;
        },Task.UI_THREAD_EXECUTOR);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showContentView(int position){
        Garbage garbage = presenter.getGarbageList().get(position);
        int type = garbage.getType();

        ContentDialogHolder holder;
        if(contentDialog == null){
            contentView = LayoutInflater.from(this).inflate(R.layout.content_view,linearLayout,false);
            contentDialog = new MyPopupWindow(this,contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder = new ContentDialogHolder(contentView);
            contentView.setTag(holder);
        }
        holder = (ContentDialogHolder) contentView.getTag();
        holder.tvGarbageTitle1.setText(TypeMap.getTypeName(type) + "是什么?");
        holder.tvGarbageContent1.setText(garbage.getExplain());
        holder.tvGarbageTitle2.setText("有什么常见的" + TypeMap.getTypeName(type) + "?");
        holder.tvGarbageContent2.setText(garbage.getContain());
        holder.tvTip.setText(garbage.getTip());

        contentDialog.showAtLocation(linearLayout,Gravity.BOTTOM,0,0);
    }

    @Override
    public void setPresenter(Contract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public Context getContext() {
        return this;
    }

    static class ContentDialogHolder{

        private TextView tvGarbageTitle1;
        private TextView tvGarbageTitle2;
        private TextView tvGarbageContent1;
        private TextView tvGarbageContent2;
        private TextView tvTip;

        private ContentDialogHolder(View view){
            tvGarbageTitle1 = view.findViewById(R.id.tv_garbage_type_title1);
            tvGarbageContent1 = view.findViewById(R.id.tv_garbage_type_content1);
            tvGarbageTitle2 = view.findViewById(R.id.tv_garbage_type_title2);
            tvGarbageContent2 = view.findViewById(R.id.tv_garbage_type_content2);
            tvTip = view.findViewById(R.id.tv_tip);
        }

    }
}
