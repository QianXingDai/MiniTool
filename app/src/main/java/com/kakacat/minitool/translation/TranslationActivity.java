package com.kakacat.minitool.translation;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kakacat.minitool.R;
import com.kakacat.minitool.common.ui.view.MyPopupWindow;
import com.kakacat.minitool.common.util.SystemUtil;
import com.kakacat.minitool.common.util.UiUtil;
import com.kakacat.minitool.translation.adapter.CollectionAdapter;
import com.kakacat.minitool.translation.adapter.LanguageAdapter;


public class TranslationActivity extends AppCompatActivity implements Contract.View {

    private Contract.Presenter presenter;

    private LinearLayout linearLayout;
    private EditText editText;
    private TextView tvOutput;
    private TextView tvFrom;
    private TextView tvTo;
    private MyPopupWindow collectionDialog;
    private MyPopupWindow selectLanguageDialog1;
    private MyPopupWindow selectLanguageDialog2;

    private LayoutInflater inflater;
    private CollectionAdapter collectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        initData();
        initView();
    }

    @Override
    public void initData() {
        inflater = LayoutInflater.from(this);
        getPresenter().initData();
    }

    @Override
    public void initView() {
        setSupportActionBar(findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_action_back);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        linearLayout = findViewById(R.id.linear_layout);
        editText = findViewById(R.id.edit_text);
        tvOutput = findViewById(R.id.tv_output);
        tvFrom = findViewById(R.id.tv_from);
        tvTo = findViewById(R.id.tv_to);
    }

    @Override
    public Contract.Presenter getPresenter() {
        if (presenter == null) {
            presenter = new Presenter(this);
        }
        return presenter;
    }

    @Override
    public void showCollectionWindow() {
        if (collectionDialog == null) {
            View view = inflater.inflate(R.layout.collection_layout, linearLayout, false);
            collectionDialog = new MyPopupWindow(this, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            RecyclerView rv = view.findViewById(R.id.rv);
            rv.setAdapter(getCollectionAdapter());
            rv.setLayoutManager(new LinearLayoutManager(this));
        }
        collectionDialog.showAtLocation(linearLayout, Gravity.BOTTOM, 0, 0);
    }

    @Override
    public void onAddToMyFavouriteCallBack(String s) {
        UiUtil.showSnackBar(linearLayout, s);
        collectionAdapter.notifyDataSetChanged();
    }

    @Override
    public void showSelectLanguageWindow(int flag) {
        if (flag == 1) {
            if (selectLanguageDialog1 == null) {
                View view = inflater.inflate(R.layout.select_from_window, linearLayout, false);
                selectLanguageDialog1 = new MyPopupWindow(this, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                RecyclerView rv = view.findViewById(R.id.rv_from);
                LanguageAdapter languageAdapter = new LanguageAdapter(presenter.getLanguageList1());
                languageAdapter.setOnClickListener((v, position) -> {
                    tvFrom.setText(presenter.getLanguageList1().get(position));
                    selectLanguageDialog1.dismiss();
                });
                rv.setAdapter(languageAdapter);
                rv.setLayoutManager(new LinearLayoutManager(this));
            }
            selectLanguageDialog1.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);
        } else if (flag == 2) {
            if (selectLanguageDialog2 == null) {
                View view = inflater.inflate(R.layout.select_from_window, linearLayout, false);
                selectLanguageDialog2 = new MyPopupWindow(this, view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                RecyclerView rv = view.findViewById(R.id.rv_from);
                LanguageAdapter languageAdapter = new LanguageAdapter(presenter.getLanguageList2());
                languageAdapter.setOnClickListener((v, position) -> {
                    tvTo.setText(presenter.getLanguageList2().get(position));
                    selectLanguageDialog2.dismiss();
                });
                rv.setAdapter(languageAdapter);
                rv.setLayoutManager(new LinearLayoutManager(this));
            }
            selectLanguageDialog2.showAtLocation(linearLayout, Gravity.CENTER, 0, 0);
        }
    }

    @Override
    public void onRequestCallBack(String result, String warn) {
        if (!TextUtils.isEmpty(result)) {
            tvOutput.setText(result);
        }
        UiUtil.showToast(this, warn);
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_open_collect: {
                showCollectionWindow();
                break;
            }
            case R.id.iv_clear: {
                editText.setText("");
                break;
            }
            case R.id.tv_from: {
                showSelectLanguageWindow(1);
                break;
            }
            case R.id.tv_to: {
                showSelectLanguageWindow(2);
                break;
            }
            case R.id.fab: {
                presenter.requestData(editText.getText().toString(), tvFrom.getText(), tvTo.getText());
                break;
            }
            case R.id.iv_copy: {
                SystemUtil.copyToClipboard(this, "translate", tvOutput.getText());
                UiUtil.showSnackBar(linearLayout, "复制完成");
                break;
            }
            case R.id.iv_collect: {
                presenter.addToMyFavourite(editText.getText().toString(), tvOutput.getText().toString());
                break;
            }
            case R.id.bt_back: {
                if (selectLanguageDialog1 != null && selectLanguageDialog1.isShowing()) {
                    selectLanguageDialog1.dismiss();
                }
                if (selectLanguageDialog2 != null && selectLanguageDialog2.isShowing()) {
                    selectLanguageDialog2.dismiss();
                }
                break;
            }
        }
    }

    private CollectionAdapter getCollectionAdapter() {
        if (collectionAdapter == null) {
            collectionAdapter = new CollectionAdapter(presenter.getCollectionList());
        }
        return collectionAdapter;
    }
}
